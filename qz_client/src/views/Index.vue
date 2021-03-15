<template>
	<div>
		<!-- 轮播图 -->
		<swiper :options="swiperOption" ref="mySwiper">
			<!-- slides -->
			<swiper-slide v-for="(slide, index) in swiperImg" :key="index">
				<div
					class="swiper-img"
					:style="{ 'background-image': 'url(' + slide + ')' }"
				></div>
			</swiper-slide>
			<div class="swiper-pagination" slot="pagination"></div>
			<div
				class="swiper-button-prev swiper-button-white"
				slot="button-prev"
			></div>
			<div
				class="swiper-button-next swiper-button-white"
				slot="button-next"
			></div>
		</swiper>
		<div class="wenhua">
			<div class="yifu">
				<div class="ekbox">
					<div class="shuangk">
						<div class="yik" style="margin-right: 0.625rem">
							<img
								src="../assets/images/64380cd7912397dd9d578d615682b2b7d0a287ac.jpg"
								alt
							/>
						</div>
						<div class="yik" style="margin-right: 0.625rem">
							<img
								src="../assets/images/cf1b9d16fdfaaf5193fb95da835494eef01f7abe.jpg"
								alt
							/>
						</div>
					</div>
					<ul class="dais">
						<li
							class="dotou"
							v-for="(item, index) in religions.slice(0, 7)"
							:key="index"
						>
							<router-link :to="'/qzReligion/' + item.id">
								<h5 class="wend">
									<span class="dan">.</span>
									{{ item.title }}
								</h5>
								<div class="tishi">{{ item.time }}</div>
								<div class="clear"></div>
							</router-link>
						</li>
					</ul>
					<router-link to="/qzReligion">
						<div class="gengchao">更多>></div>
					</router-link>
				</div>
			</div>
		</div>
		<div class="diyu">
			<div class="baichang">
				<ul class="mingdi">
					<li
						class="hansan"
						v-for="(item, index) in regions.slice(0, 3)"
						:key="index"
					>
						<router-link :to="'/qzRegion/' + item.id">
							<h5 class="hstit">{{ item.title }}</h5>
							<div class="boxsm">
								<img :src="item.imagePath" alt />
							</div>
							<p class="daiha" :digest="item.digest">
								{{ item.digest | ellipsis }}
							</p>
						</router-link>
					</li>
					<div class="clear"></div>
				</ul>
				<router-link to="/qzRegion">
					<div class="gengchao2">更多>></div>
				</router-link>
			</div>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex'
export default {
	data() {
		return {
			// 轮播图
			swiperImg: [
				require('../assets/images/lbt1.jpg'),
				require('../assets/images/lbt2.jpg'),
			],
			swiperOption: {
				speed: 1000,
				autoplay: {
					delay: 3000,
					disableOnInteraction: true, // 手动切换之后继续自动轮播
				},
				loop: true,
				observer: {
					observer: true,
					observerParents: true,
				},
				pagination: {
					el: '.swiper-pagination',
					clickable: true, // 允许点击小圆点跳转
				},
				navigation: {
					nextEl: '.swiper-button-next',
					prevEl: '.swiper-button-prev',
				},
			},
			query: {
				num: 0,
				size: 0,
			},
		}
	},
	mounted() {
		this.$store.dispatch('getRegionList', this.query)
		this.$store.dispatch('getReligionList', this.query)
	},
	computed: {
		...mapState(['regions', 'religions']),
		swiper() {
			return this.$refs.mySwiper.$swiper
		},
	},
	filters: {
		// 版本号显示12位，超过12位显示...
		ellipsis: function (value) {
			if (!value) return ''
			if (value.length > 60) {
				return value.slice(0, 60) + '...'
			}
			return value
		},
	}
}
</script>

<style lang="stylus" scoped>
.swiper-img
	width 100%
	height 38.3125rem
	overflow hidden
	text-align center
	background-position center
	background-repeat no-repeat
.diyu
	width 100%
	background url('../assets/images/diyu.jpg') no-repeat
	background-position top center
	// height 48.3125rem
	overflow hidden
	.baichang
		width 82.25rem
		margin 13.25rem auto 3rem
		background #fff
		// margin-top 13.25rem
		overflow hidden
		.mingdi
			padding 0
			width 78rem
			margin 0 auto
			margin-top 1.25rem
			.hansan
				width 25rem
				float left
				margin-right 1rem
				.hstit
					margin-top 0.3125rem
					font-size 1.125rem
					font-weight bold
					color #000
					text-align left
					line-height 2em
					margin-bottom 0.75rem
					transition 0.3s
					&:hover
						color #e60012
				.boxsm
					img
						width 25rem
						height 15.8125rem
				.daiha
					width 90%
					font-size 0.875rem
					color #000
					text-align left
					line-height 1.8em
					margin-top 4%
					overflow hidden
					transition 0.3s
					&:hover
						color #e60012
		.gengchao2
			font-size 0.875rem
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
.wenhua
	width 100%
	// height 39.5625rem
	background url('../assets/images/wenhua.jpg') no-repeat top center
	overflow hidden
	.yifu
		width 78rem
		margin 0 auto
		overflow hidden
		.ekbox
			// width 38.125rem
			margin-top 3.875rem
			.shuangk
				margin-bottom 1.5rem
				display flex
				.yik
					img
						width 18.4375rem
						height 11.6875rem
			.dais
				padding 0
				.dotou
					margin 0.3125rem 0
					a
						display flex
						.wend
							margin-right 1.25rem
							font-size 0.875rem
							color #000
							text-align left
							line-height 2.4em
							font-weight 100
							transition 0.3s
							display flex
							&:hover
								color #e60012
							.dan
								display inline-block
								font-size 1.5rem
								margin-top -0.375rem
								margin-right 0.75rem
						.tishi
							font-size 0.875rem
							color #a8a8a8
							line-height 2.4em
			.gengchao
				font-size 0.875rem
				color #000
				text-align center
				line-height 2.6875rem
				border 0.0625rem solid #000
				width 6.5rem
				transition 0.3s
				margin 3%
				&:hover
					background #000
					color #fff
@media only screen and (min-width 20rem) and (max-width 48rem)
	.swiper-img
		height 28.3125rem
		width 100%
		background-size 100% 100%
	.diyu
		background-size 100% 100%
		.baichang
			width 70%
			margin 10rem auto 3rem
			.mingdi
				width 100%
				margin-top 1.25rem
				display flex
				justify-content center
				.hansan
					width 20rem
					float left
					&:nth-last-of-type(2)
						display none
					&:nth-last-of-type(1)
						display none
					.hstit
						font-size 1.25rem
					.boxsm
						img
							width 20rem
							height 10.8125rem
					.daiha
						font-size 1.25rem
			.gengchao2
				font-size 1rem
	.wenhua
		width 100%
		background-size 100% 100%
		.yifu
			width 100%
			.ekbox
				.shuangk
					padding-left 1.25rem
					.yik
						img
							width 13.4375rem
							height 6.6875rem
				.dais
					padding 0 1.25rem
					.dotou
						a
							.wend
								font-size 1.25rem
								.dan
									font-size 1.25rem
							.tishi
								font-size 1.25rem
				.gengchao
					font-size 1rem
</style>