<template>
	<!-- 导航栏 -->
	<div class="nav_container">
		<router-link to="/">
			<img :src="title_img" alt="标题" class="titleImg" />
		</router-link>
		<nav>
			<div class="nav_item" v-for="(item,index) in navigations" :key="index" v-show="item.isShow">
				<router-link :to="item.url">
					<span>{{item.menuName}}</span>
				</router-link>
			</div>
		</nav>
	</div>
</template>

<script>
import { mapState } from 'vuex'
export default {
	data() {
		return {
			params: {
				keyword: '',
				num: 1,
				size: 0
			},
			// 标题图片地址
			title_img: require('../assets/images/title.png')
		}
	},
	created() {},
	mounted() {
		this.$store.dispatch('getNavigationList', this.params)
	},
	updated() {},
	methods: {},
	watch: {},
	computed: {
		...mapState(['navigations'])
	}
}
</script>

<style lang="stylus" scoped>
.nav_container
	width 100%
	display flex
	justify-content space-around
	background-color #fff
	.titleImg
		margin 1.25rem 3.125rem
	nav
		display flex
		justify-content space-around
		.nav_item
			a
				display flex
				flex-flow column
				span
					font-size 1.125rem
					color #000
					line-height 5.75rem
					width 6.375rem
					text-align center
					transition 0.3s
					font-weight 500
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.nav_container
		.titleImg
			display none
		nav
			display flex
			justify-content space-around
			.nav_item
				a
					span
						font-size 1.375rem
						line-height 5.75rem
						width 5.1rem
</style>