<!--  -->
<template>
	<div class="situation">
		<div class="mainHead">
			<h3>地方戏剧</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>地方戏剧</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<div class="con_bg1">
				<div class="gl_con">
					<div class="gl_l">
						<div class="gl_tit">泉州地方戏曲库</div>
						<div class="gl_list">
							<ul>
								<li v-for="(item,index) in dramaClassName" :key="index">
									<router-link :to="'/qzDrama/'+item">{{item}}</router-link>
								</li>
							</ul>
						</div>
					</div>
					<div class="gl_r">
						<keep-alive><router-view></router-view></keep-alive>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex'
import 'videojs-flash'
import 'videojs-hotkeys'
export default {
	data() {
		return {
			params: {
				keywords: '',
				num: 1,
				size: 30
			}
		}
	},

	components: {},

	computed: {
		...mapState(['dramaClassName'])
	},

	mounted() {
		this.$store.dispatch('getDramaClassName')
	},

	methods: {
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
			font-size .9375rem
			line-height 3.75rem
	.mainBody
		margin 1.875rem .625rem
		// height 37.5rem
		display flex
		flex-flow row wrap
		.con_bg1
			background url('../assets/images/16qtk_con1.jpg') repeat-x top #ffffff
			width 90%
			margin 0 auto
			padding 1.875rem 1.25rem
			.gl_con
				display flex
				.gl_l
					background url('../assets/images/16qtk_gl_con.jpg') repeat-y left
					.gl_tit
						background url('../assets/images/16qtk_gl_tit.jpg') no-repeat
						width 15.875rem
						font-size 1.5rem
						height 5.3125rem
						line-height 5.3125rem
						text-align center
						font-family 'Microsoft YaHei'
						color #fff
					.gl_list
						margin .9375rem auto 0
						width 14rem
						// color #9a0c0c
						ul
							margin 0
							padding 0
							list-style-type none
							li
								background url('../assets/images/16qtk_gl_bar.jpg') no-repeat center
								height 3.2rem
								line-height 2.75rem
								margin-top 1rem
								font-size 1.125rem
								font-family 'Microsoft YaHei'
								color #5b4529
								text-align center
								a
									color #5b4529
									&.on
										color #5b4529
				.gl_r
					width 53.125rem
					height 34.375rem
					margin 0 1.875rem
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
			width 100%
			margin 1.875rem 0
			.con_bg1
				margin 0
				padding 1rem
				.gl_con
					.gl_l
						width 40%
						.gl_tit
							width 100%
							font-size 1rem
						.gl_list
							margin 0
							width 100%
							ul
								li
									background-size 100% 80%
									font-size 1rem
					.gl_r
						width 100%
						height auto
						margin 0 1.875rem
</style>
