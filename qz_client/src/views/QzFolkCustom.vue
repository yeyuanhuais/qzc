<!--  -->
<template>
	<div class="situation">
		<div class="mainHead">
			<h3>民风民俗</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>民风民俗</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<div class="box">
				<div class="listDiv" v-for="item in folkCustoms" :key="item.id" v-show="item.isShow">
					<p>
						<router-link :to="'/qzFolkCustom/'+item.id" :title="item.title">{{item.title|ellipsis}}</router-link>
						<span>{{item.time}}&nbsp;&nbsp;{{item.source}}</span>
					</p>
					<div>
						<router-link :to="'/qzFolkCustom/'+item.id" class="left">
							<img v-if="item.imagePath" :src="item.imagePath" />
							<img v-else src="../assets/images/洛阳桥.jpg" />
						</router-link>
						<router-link :to="'/qzFolkCustom/'+item.id" class="righta">
							<span
								class="right"
								v-html="msg(item.content).length > 150 ? msg(item.content).slice(0, 150) + '...' :msg(item.content)"
							></span>
						</router-link>
					</div>
				</div>
			</div>
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:current-page.sync="query.num"
				:page-sizes="[5,10, 20, 30, 40]"
				:page-size="query.size"
				layout="total,sizes, prev, pager, next"
				:total="articleCount"
			></el-pagination>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex'
export default {
	data() {
		return {
			query: {
				size: 10,
				num: 1
			}
		}
	},
	computed: {
		...mapState(['folkCustoms', 'articleCount'])
	},
	mounted() {
		this.$store.dispatch('getFolkCustomList', this.query)
	},
	methods: {
		handleSizeChange(val) {
			this.query.size = val
			this.$store.dispatch('getFolkCustomList', this.query)
		},
		handleCurrentChange(val) {
			this.query.num = val
			this.$store.dispatch('getFolkCustomList', this.query)
		}
	},
	filters: {
		// 版本号显示12位，超过12位显示...
		ellipsis: function(value) {
			if (!value) return ''
			if (value.length > 13) {
				return value.slice(0, 13) + '...'
			}
			return value
		}
	}
}
</script>
<style lang='stylus' scoped>
.situation
	padding 0 8.125rem
	.mainHead
		height 3.75rem
		border-bottom 0.0625rem #ccc solid
		// overflow hidden
		display flex
		justify-content space-between
		h3
			margin 0
			width 10rem
			// height 3.625rem
			border-bottom 0.125rem #117750 solid
			font-size 1.5rem
			color #117750
			line-height 3.625rem
			padding 0 1.25rem 0 2.5rem
		.el-breadcrumb
			font-size 0.9375rem
			line-height 3.75rem
	.mainBody
		.box
			margin-top 1.25rem
			.listDiv
				height 11.125rem
				border #f2f1ec solid 0.0625rem
				background-color #ffffff
				margin-top 0.625rem
				display flex
				align-items center
				flex-direction column
				p
					background url('../assets/images/bg_list.jpg') no-repeat right 0.1125rem
					height 3.125rem
					border-bottom #e9e9e9 dashed 0.135rem
					width 100%
					display flex
					justify-content space-around
					line-height 3.4375rem
					margin 0
					a
						font-size 1rem
						font-weight bold
						color #463626
						margin-right 8.25rem
						height 2.5rem
					span
						margin-right 11.25rem
						height 2.5rem
						font-size 0.75rem
				div
					display flex
					width 80%
					.left
						width 5.625rem
						height 5.5625rem
						margin-top 0.9375rem
						img
							height 5.8125rem
							width 5.5625rem
					.righta
						.right
							width 100%
							margin-left 0.9375rem
							display block
							background #f6f3ec
							border #f6f3ec solid 0.0625rem
							height 4.3125rem
							margin-top 0.9375rem
							padding 0.625rem 0.9375rem 0.625rem 1.25rem
							color #3e3e36
							line-height 1.5rem
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.situation
		padding 0 2.125rem
		.mainHead
			display flex
			justify-content space-between
			.el-breadcrumb
				font-size .9375rem
				line-height 3.75rem
		.mainBody
			.box
				.listDiv
					height auto
					div
						width 100%
						.righta
							.right
								width 90%
								height 4.3125rem
								line-height 1.3rem
								font-size .9375rem
</style>
