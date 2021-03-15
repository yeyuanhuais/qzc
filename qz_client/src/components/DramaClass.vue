<template>
	<div class="list">
		<ul>
			<li v-for="item in dramas" :key="item.id">
				·
				<router-link :to="'/qzDrama/'+item.dramaClass+'/'+item.id">{{item.title}}</router-link>
			</li>
		</ul>
	</div>
</template>

<script>
import {mapState} from 'vuex'
export default {
	computed: {
		...mapState(['dramas'])
	},
	mounted(){
		this.$store.dispatch('getDramaList',this.$route.params.className)
	},
	watch:{
		'$route.params.className'(){
			this.$store.dispatch('getDramaList',this.$route.params.className)
		}
	}
}
</script>

<style lang="stylus" scoped>
.list
	margin .9375rem auto 0
	ul
		display flex
		flex-flow row wrap
		li
			width 50%
			display inline
			line-height 2.1875rem
			font-size .875rem
			a
				&:link
					color rgb(79,81,83)
				&:visited
					color rgb(79,81,83)
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.list
		margin .9375rem 0 .9375rem .9375rem
		ul
			padding 0
</style>