<template>
	<div>
		<h4>发表评论</h4>
		<hr />
		<textarea class="textarea" placeholder="请输入要评论的内容" v-model="commentList.content"></textarea>
		<el-button type="primary" @click="postMsg">发表评论</el-button>
		<!-- 评论列表区域 -->
		<div class="cmt-list">
			<div class="cmt-item-root" v-for="(item, i) in cmtlist" :key="i">
				<div
					class="cmt-item-title-root"
				>第{{ i+1 }}楼&nbsp;用户：{{ item.userName }}&nbsp;发表时间：{{ item.createTime | dateString('YYYY年M月D日 HH:mm:ss') }}</div>
				<div class="cmt-item-body-root">{{ item.content }}</div>
				<div class="cmt-item-son" v-for="(children, i) in item.commentsChildren" :key="i" >
					<div
						class="cmt-item-title-son"
					>第{{ i+1 }}楼&nbsp;用户：{{ children.userName }}&nbsp;发表时间：{{ children.createTime | dateString('YYYY年M月D日 HH:mm:ss') }}</div>
					<div class="cmt-item-body-son">{{ children.content }}</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			cmtlist: [], // 评论数组
			commentList: {
				content: ''
			}
		}
	},
	mounted() {
	},
	methods: {
		async getCommentsByOwner() {
			// 根据页数来获取评论的数据
			const { data: res } = await this.$api.comment.getCommentsByOwnerId(
				this.commentList.ownerId,
				this.commentList.ownerType
			)
			console.log(res)
			if (res.code === 1) {
				return (this.cmtlist = this.cmtlist.concat(res.response))
			} else {
				return this.$message.error(res.message)
			}
		},
		postMsg() {
			// 点击发表评论：
			// 如果用户没有填写评论内容，则阻止其发表评论
			if (this.commentList.content.trim().length <= 0) {
				return this.$message.error('请填写评论内容！')
			}
			setTimeout(async () => {
				const { data: res } = await this.$api.comment.addRoot(this.commentList)
				if (res.code === 1) {
					this.$message.success('评论成功')
					this.commentList.content = ''
					this.getCommentsByOwner()
				}
			},3000)
		}
	},
	watch: {
		article(value) {
			this.$nextTick(() => {
				this.commentList.ownerId = this.article.id
				this.commentList.ownerType = this.article.className
				this.commentList.userId = this.user.id
				this.commentList.userName = this.user.userName
        if(Object.keys(this.user).length!==0){
					this.getCommentsByOwner()
				}
			})
		},
		user(value) {
			this.$nextTick(() => {
				this.commentList.userId = this.user.id
				this.commentList.userName = this.user.userName
			})
		}
	},
	props:['article','user']
}
</script>

<style lang="stylus" scoped>
h4
	margin .625rem 0 .625rem 1.25rem
.textarea
	font-size .875rem
	margin 1.25rem
.el-button
	display flex
	justify-content center
	font-size 1rem
	width 6.625rem
	height	2.5rem
	margin-left 1.875rem
.cmt-list
	margin-top .25rem
	.cmt-item-root
		line-height 1.875rem
		margin 0 1.25rem
		.cmt-item-title-root
			font-size .875rem
			background-color #ddd
		.cmt-item-body-root
			font-size .8125rem
			text-indent 2em
		.cmt-item-son
			line-height 1.875rem
			margin 0 1.25rem
			.cmt-item-title-root
				font-size .875rem
				background-color #ddd
			.cmt-item-body-root
				font-size .8125rem
				text-indent 2em
</style>
