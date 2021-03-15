<template>
	<el-container>
		<el-header>
			<div>
				<img :src="require('../../assets/images/admin.jpg')" alt="图标" />
				<span>后台管理</span>
			</div>
			<div class="btn">

			<el-button type="info" @click="loginOut">退出</el-button>
			<el-button type="info" @click="back">返回首页</el-button>
			</div>
		</el-header>
		<el-container>
			<el-aside :width="isCollapse?'4rem':'12.5rem'">
				<div class="toggle-button" @click="toggleCollapse">|||</div>
				<el-menu
					background-color="#333744"
					text-color="#fff"
					active-text-color="#409eff"
					unique-opened
					:collapse="isCollapse"
					:collapse-transition="false"
					:router="true"
					:default-active="activePath"
				>
					<el-menu-item
						:index="item.url+ ''"
						v-for="item in menuList"
						:key="item.id"
						@click="saveNameState(item.url)"
					>
						<template slot="title">
							<i class="el-icon-message"></i>
							<span>{{item.name}}</span>
						</template>
					</el-menu-item>
				</el-menu>
			</el-aside>
			<el-main>
				<router-view></router-view>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
export default {
	data() {
		return {
			menuList: [
				{ id: 1, name: '评论管理', url: '/admin/comments' },
				{ id: 2, name: '游记管理', url: '/admin/travelNotes' },
				{ id: 3, name: '导航栏管理', url: '/admin/navigation' },
				{ id: 4, name: '文章管理', url: '/admin/article' },
				{ id: 5, name: '用户管理', url: '/admin/user' },
				{ id: 6, name: '景点管理', url: '/admin/region' }
			],
			isCollapse: false,
			activePath: this.$route.path
		}
	},
	created() {
	},
	methods: {
		loginOut() {
			this.$store.dispatch('logout')
			this.$router.push('/')
		},
		toggleCollapse() {
			this.isCollapse = !this.isCollapse
		},
		saveNameState(activePath) {
			this.activePath = activePath
		},
		back(){
			this.$router.push('/')
		}
	}
}
</script>

<style lang="stylus" scoped>
.el-container
	height 100%
	width 100%
	min-height 43.4375rem
.el-header
	background-color #373d41
	color #ffffff
	line-height 3.75rem
	display flex
	justify-content space-between
	padding-left 0
	align-items center
	font-size 1.25rem
	.btn
		.el-button
			display flex
	>div
		display flex
		align-items center
		span
			margin-left .9375rem
		img
			width 3.75rem
			height 3.75rem
.el-aside
	color #333
	background-color #333744
	.el-menu
		border-right none
.el-main
	background-color #eaedf1
.template>i
	margin-left .625rem
.toggle-button
	background-color #4a5064
	font-size .625rem
	line-height 1.5rem
	color #fff
	text-align center
	letter-spacing 0.2em
	cursor pointer
header div
	margin 0 0 0 .3125rem
</style>
