<!--评论-->
<template>
	<div>
		<!-- 导航 -->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>评论</el-breadcrumb-item>
			<el-breadcrumb-item>评论列表</el-breadcrumb-item>
		</el-breadcrumb>
		<!-- 卡片 -->
		<el-card class="box-card">
			<el-row :gutter="20">
				<el-col :span="7">
					<el-input placeholder="请输入内容" v-model="query.keyword" @clear="getCommentList" clearable>
						<el-button slot="append" icon="el-icon-search" @click="getCommentList"></el-button>
					</el-input>
				</el-col>
			</el-row>
			<el-table :data="commentList" border stripe>
				<el-table-column type="expand" prop="commentsChildren">
					<template slot-scope="scope">
						<el-table :data="scope.row.commentsChildren">
							<el-table-column type="index" label="#"></el-table-column>
							<el-table-column prop="pid" label="父级评论id" sortable align="center">
								<template slot-scope="scope">
									<span>{{scope.row.pid}}</span>
								</template>
							</el-table-column>
							<el-table-column prop="content" label="评论内容" sortable align="center">
								<template slot-scope="scope">
									<span>{{scope.row.content}}</span>
								</template>
							</el-table-column>
							<el-table-column prop="createTime" label="创建时间" sortable align="center">
								<template slot-scope="scope">
									<span>{{scope.row.createTime | dateString('YYYY年M月D日 HH:mm:ss') }}</span>
								</template>
							</el-table-column>
							<el-table-column prop="ownerId" label="被评论id" sortable align="center">
								<template slot-scope="scope">
									<span>{{scope.row.ownerId}}</span>
								</template>
							</el-table-column>
							<el-table-column label="操作" width="130px">
								<template slot-scope="scope">
									<el-button type="danger" icon="el-icon-delete" circle @click="deleteBox(scope.row)"></el-button>
								</template>
							</el-table-column>
						</el-table>
					</template>
				</el-table-column>
				<el-table-column type="index" label="#"></el-table-column>
				<el-table-column prop="pid" label="父级评论id"></el-table-column>
				<el-table-column prop="content" label="评论内容"></el-table-column>
				<el-table-column prop="createTime" label="创建时间">
					<template slot-scope="scope">
						<span>{{scope.row.createTime | dateString('YYYY年M月D日 HH:mm:ss') }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="ownerId" label="被评论id"></el-table-column>
				<el-table-column label="操作" width="130px">
					<template slot-scope="scope">
						<el-button type="danger" icon="el-icon-delete" circle @click="deleteBox(scope.row)"></el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:current-page.sync="query.num"
				:page-sizes="[5,10, 20, 30, 40]"
				:page-size="query.size"
				layout="total,sizes, prev, pager, next"
				:total="count"
			></el-pagination>
		</el-card>
	</div>
</template>

<script>
export default {
	data() {
		return {
			query: {
				keyword: '',
				size: 5,
				num: 1
			},
			count: 0,
			// 评论列表
			commentList: []
		}
	},
	created() {
		this.getCommentList()
	},
	methods: {
		// 评论列表
		async getCommentList() {
			const { data: res } = await this.$api.comment.getAll(this.query)
			console.log(res)
			if (res.code !== 1) {
				return this.$message.error('获取评论列表失败！')
			}
			this.commentList = res.response
			this.count = res.count
		},
		// 删除
		deleteBox(tn) {
			const confirmResult = this.$confirm(
				'此操作将永久删除该评论, 是否继续?',
				'提示',
				{
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}
			)
				.then(async () => {
					const { data: res } = await this.$api.comment.delete(tn.id)
					if (res.code !== 1) {
						return this.$message.error('删除评论失败！')
					}
					this.getCommentList()
					this.$message({
						type: 'success',
						message: '删除成功!'
					})
				})
				.catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					})
				})
		},
		handleSizeChange(val) {
			this.query.size = val
			this.getCommentList()
		},
		handleCurrentChange(val) {
			this.query.num = val
			this.getCommentList()
		}
	}
}
</script>
<style lang='stylus' scoped>
.el-table__expanded-cell
	padding 0 0.3125rem
</style>