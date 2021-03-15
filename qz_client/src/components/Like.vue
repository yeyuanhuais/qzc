<template>
	<div id="like" :class="like?'active':''">
		<span class="img"></span>
		<span>{{count}}</span>
	</div>
</template>

<script>
export default {
	props: {
		count: {
			type: Number,
			required: false
		},
		like: {
			type: Boolean,
			required: true,
			default: false
		}
	}
}
</script>

<style lang="stylus" scoped>
#like
	width 6.25rem
	height 1.875rem
	line-height 1.875rem
	display flex
	align-items center
	justify-content center
	font-size .9375rem
	margin 1.875rem .625rem
	color #42bd56
	border 0.0625rem solid #42bd56
	.img
		margin-right .25rem
		width 1.25rem
		height 1.25rem
		background url('https://img3.doubanio.com/f/talion/c689ab2369da387f2cb0d4da4a22614d7f048bf9/pics/card/ic_heart_green.svg') no-repeat // img3.doubanio.com/f/talion/c689ab2369da387f2cb0d4da4a22614d7f048bf9/pics/card/ic_heart_green.svg) no-repeat;
		background-size 100% 100%
	&.active
		color #333
		border 0.0625rem solid #bfbfbf
		.img
			background-image url('https://img3.doubanio.com/f/talion/0129352875382ff0544020161709c2ae9b00c737/pics/card/ic_heart_red.svg') // img3.doubanio.com/f/talion/0129352875382ff0544020161709c2ae9b00c737/pics/card/ic_heart_red.svg);
			background-size 100% 100%
</style>