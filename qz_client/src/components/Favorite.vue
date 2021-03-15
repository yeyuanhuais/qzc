<template>
	<div id="favorite" :class="favorite?'active':''">
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
		favorite: {
			type: Boolean,
			required: true,
			default: false
		}
	}
}
</script>

<style lang="stylus" scoped>
#favorite
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
		background url('../assets/images/fav.svg') no-repeat
		background-size 100% 100%
	&.active
		color #333
		border 0.0625rem solid #bfbfbf
		.img
			background-image url('../assets/images/fav_on.svg')
			background-size 100% 100%
</style>