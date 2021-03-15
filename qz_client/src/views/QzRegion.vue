<template>
	<div class="situation">
		<div class="mainHead">
			<h3>景点</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>景点</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<div class="jd">
				<p>
					泉州是福建省一个著名的旅游城市，这里也是我国侨乡和台湾的同胞的祖籍地，这里和台湾隔海相望，
					在这里我们可以看到很多的旅游景点，既有一些自然景观有一些人文景观，泉州是我国一座著名的历史
					文化名城，来到这里旅游的时候美丽的自然景观会使你流连忘返，尤其是春天的时候，这里的景色会让
					你觉得赞不绝口。
				</p>
				<swiper :options="swiperOption" ref="mySwiper">
					<!-- slides -->
					<swiper-slide v-for="(item,index) in regions" :key="index">
						<div class="hansan">
							<h5 class="hstit">{{item.title}}</h5>
							<div class="boxsm">
								<img :src="item.imagePath" alt />
							</div>
							<p class="daiha" :digest="item.digest">{{item.digest| ellipsis}}</p>
							<router-link :to="'/qzRegion/'+item.id">
								<div class="gengchao2">更多>></div>
							</router-link>
						</div>
					</swiper-slide>
					<div class="swiper-pagination" slot="pagination"></div>
					<div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
					<div class="swiper-button-next swiper-button-white" slot="button-next"></div>
				</swiper>
			</div>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex'
export default {
	data() {
		return {
			qurey:{
				num:0,
				size:0
			},
			swiperOption: {
				// speed: 500,
				// autoplay: {
				// 	delay: 3000,
				// 	stopOnLastSlide: false,
				// 	disableOnInteraction: false // 手动切换之后继续自动轮播
				// },
				loop: true,
				observer: {
					observer: true,
					observerParents: true
				},
				pagination: {
					el: '.swiper-pagination',
					clickable: true // 允许点击小圆点跳转
				},
				navigation: {
					nextEl: '.swiper-button-next',
					prevEl: '.swiper-button-prev'
				},
				slidesPerView: 3
			}
		}
	},
	computed: {
		...mapState(['regions']),
		swiper() {
			return this.$refs.mySwiper.$swiper
		}
	},
	filters: {
		// 版本号显示12位，超过12位显示...
		ellipsis: function(value) {
			if (!value) return ''
			if (value.length > 60) {
				return value.slice(0, 60) + '...'
			}
			return value
		}
	},
	mounted() {
		this.$store.dispatch('getRegionList',this.qurey)
	}
}
</script>

<style lang="stylus" scoped>
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
		.jd
			// height auto
			background-color #f7f7f7
			margin 0 auto
			margin-top 1.875rem
			padding-top .3125rem
			p
				text-indent 2.5rem
				width 65%
				margin 2.5rem auto
				line-height 1.75rem
				padding .625rem 0 1.25rem 0
			.swiper-slide
				height 40.375rem
				// margin-top 3.25rem
				// margin-left 1.25rem
				margin 0 auto
				.hansan
					width 25rem
					.hstit
						margin-top .3125rem
						font-size 1.125rem
						font-weight bold
						color #000
						text-align left
						line-height 1rem
						margin-bottom .75rem
						transition 0.3s
						display flex
						justify-content center
						&:hover
							color #e60012
					.boxsm
						img
							width 25rem
							height 15.8125rem
					.daiha
						width 90%
						font-size .875rem
						color #000
						text-align left
						line-height 1.8em
						margin-top 4%
						height 5.1875rem
						overflow hidden
						transition 0.3s
						&:hover
							color #e60012
					.gengchao2
						font-size .875rem
						color #000
						text-align center
						line-height 2.6875rem
						border 0.0625rem solid #000
						width 6.5rem
						margin 0 auto
						transition 0.3s
						margin-top 2%
						margin-bottom 2%
						&:hover
							background #000
							color #fff
.clear
	clear both
.swiper-container-free-mode
	> .swiper-wrapper
		-webkit-transition-timing-function linear /* 之前是ease-out */
		-moz-transition-timing-function linear
		-ms-transition-timing-function linear
		-o-transition-timing-function linear
		transition-timing-function linear
		margin 0 auto
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
			margin 0 auto
</style>