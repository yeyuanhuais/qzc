<template>
	<!-- 时间--登录注册 -->
	<div class="headerBox">
		<div><span v-cloak>今天是：{{dateTime}}</span></div>
		<div class="unlogin" v-if="Object.keys(user).length===0">
			<router-link to="/login" @click.native="chance(1)">登录</router-link>
			<router-link to="/login" @click.native="chance(2)">注册</router-link>
		</div>
		<div class="login" v-else>
			<router-link to="/user">
				<img v-if="user.imagePath===''||user.imagePath===null" src="../assets/images/admin.jpg" alt="头像" />
				<img v-else :src="user.imagePath" alt="头像" />
			</router-link>
			<p>{{user.userName}}</p>
			<router-link to="/" @click.native="$store.dispatch('logout');">退出</router-link>
			<router-link to="/admin" v-show="user.role===false">
				进入后台
			</router-link>
		</div>
	</div>
</template>

<script>
import { SET_LOGINWAY } from '../store/mutations-type'
import { mapState } from 'vuex'
export default {
	data() {
		return {
			dateTime: this.getNowFormatDate(),
			loginWay: true
		}
	},
	methods: {
		chance(value) {
			if (value === 1) {
				this.loginWay = false
				this.$store.commit(SET_LOGINWAY, this.loginWay)
			} else if (value === 2) {
				this.loginWay = true
				this.$store.commit(SET_LOGINWAY, this.loginWay)
			}
		}
	},
	computed: {
		...mapState(['user'])
	},
	watch:{
		user(newval,oldval){
			// console.log(newval,oldval)
		}
	},
	mounted(){
	}
}
</script>

<style lang="stylus" scoped>
.headerBox
	display flex
	justify-content space-around
	background-color #000
	height 2.5rem
	line-height 2.5rem
	span
		color #fff
		font-size .75rem
	.unlogin
		text-align right
		a
			color #ffffff
			font-size .75rem
			margin-left .625rem
	.login
		display flex
		justify-content flex-end
		p
			color #ffffff
			margin 0 .9375rem
		a
			color #fff
			font-size .75rem
			margin-right .3125rem
			img
				border-radius 50%
				height 2.5rem
				width 2.5rem
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.headerBox
		height 3.125rem
		line-height 3.125rem
		span
			font-size 1.125rem
		.unlogin
			a
				font-size 1.125rem
		.login
			a
				font-size 1.125rem
</style>
