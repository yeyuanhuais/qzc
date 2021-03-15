<template>
	<div class="page">
		<div>
			<Header />
			<Navigation />
			<!-- 主体 -->
			<div class="subject">
				<keep-alive>
					<router-view></router-view>
				</keep-alive>
			</div>
			<!-- <Foot/> -->
		</div>
	</div>
</template>

<script>
import Header from '../components/Header'
import Navigation from '../components/Navigation'
import Foot from '../components/Foot'
export default {
	name: 'app',
	mounted() {},
	components: {
		Navigation,
		Header
		// Foot
	},
}
</script>

<style lang="stylus" scoped>
.page
  width 100%
  height 100%
</style>
