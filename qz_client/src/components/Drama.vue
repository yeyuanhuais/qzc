<!--  -->
<template>
	<div class="situation">
		<div class="mainHead">
			<h3>地方戏剧</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item :to="{ path: '/qzDrama' }">地方戏剧</el-breadcrumb-item>
				<el-breadcrumb-item>详细页</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<h2 class="title">{{drama.title}}</h2>
			<div class="iframeDiv">
				<iframe :src="drama.path" frameborder="0" width="640" height="480" scrolling="auto"></iframe>
			</div>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex'
export default {
	computed: {
		...mapState(['drama'])
	},
	// mounted() {
	// 	this.$store.dispatch('getDrama', this.$route.params.id)
	// }
	activated(){
		this.$store.dispatch('getDrama', this.$route.params.id)
	}
}
</script>
<style lang="stylus" scoped>
.situation
	padding 0 8.125rem
	.mainHead
		height 3.75rem
		border-bottom 0.0625rem #ccc solid
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
		.title
			width	80%
			font-size 1.875rem
			line-height 1.5
			text-align center
			margin 1.25rem auto
			color #ba0f0f
			font-weight bold
			border-bottom 0.0625rem #ccc dotted
		.iframeDiv
			display flex
			justify-content center
			iframe
				margin 2.5rem auto
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.situation
		padding 0 2.125rem
		.mainHead
			display flex
			justify-content space-between
			.el-breadcrumb
				font-size .9375rem
				line-height 3.75rem
</style>