<!--  -->
<template>
	<div class="situation">
		<div class="mainHead">
			<h3>登录/注册</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>登录/注册</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<div class="login_header_title">
				<a href="javascript:;" :class="{on:loginWay}" @click="chance(1)">注册</a>
				<a href="javascript:;" :class="{on:!loginWay}" @click="chance(2)">登录</a>
			</div>
			<div class="login_content">
				<form @submit.prevent="login" ref="formRef">
					<div :class="{on:loginWay}">
						<section class="login_verification">
							<input type="text" placeholder="用户名" v-model="registerList.userName" />
						</section>
						<section class="login_verification">
							<input type="text" placeholder="密码" v-if="showPwd" v-model="registerList.password" />
							<input type="password" placeholder="密码" v-else v-model="registerList.password" />
							<div class="switch_button" :class="showPwd?'on':'off'" @click="showPwd=!showPwd">
								<div class="switch_circle" :class="{right: showPwd}"></div>
								<span class="switch_text">{{showPwd ? 'abc' : '...'}}</span>
							</div>
						</section>
							<section class="login_verification">
								<input type="text" placeholder="确认密码" v-if="showPwd" v-model="password" />
								<input type="password" placeholder="确认密码" v-else v-model="password" />
								<div class="switch_button" :class="showPwd?'on':'off'" @click="showPwd=!showPwd">
									<div class="switch_circle" :class="{right: showPwd}"></div>
									<span class="switch_text">{{showPwd ? 'abc' : '...'}}</span>
								</div>
							</section>
						<section class="login_message">
							<input type="email" placeholder="邮箱" v-model="registerList.email" />
							<button
								:disabled="!rightEmail"
								class="get_verification"
								:class="{right_phone:rightEmail}"
								@click.prevent="getCode"
							>{{computeTime>0 ? `已发送(${computeTime}s)` : '获取验证码'}}</button>
						</section>
						<section class="login_verification">
							<input type="tel" maxlength="8" placeholder="验证码" v-model="code" />
						</section>
						<section class="login_hint">
							温馨提示：注册代表已同意
							<a to="javascript:;">《用户服务协议》</a>
						</section>
						<button class="login_submit">注册</button>
					</div>
					<div :class="{on:!loginWay}">
						<section>
							<section class="login_message">
								<input type="tel" placeholder="用户名" v-model="loginList.userName" />
							</section>
							<section class="login_verification">
								<input type="text" placeholder="密码" v-if="showPwd" v-model="loginList.password" />
								<input type="password" placeholder="密码" v-else v-model="loginList.password" />
								<div class="switch_button" :class="showPwd?'on':'off'" @click="showPwd=!showPwd">
									<div class="switch_circle" :class="{right: showPwd}"></div>
									<span class="switch_text">{{showPwd ? 'abc' : '...'}}</span>
								</div>
							</section>
							<section class="login_message">
								<input type="text" maxlength="11" placeholder="验证码" v-model="loginList.captcha" />
								<img
									class="get_verification"
									src=""
									alt="captcha"
									@click="getCaptcha"
									ref="captcha"
								/>
							</section>
						</section>
						<button class="login_submit">登录</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</template>

<script>
import {mapState} from 'vuex'
import {RECEIVE_USER,SET_LOGINWAY,USERCODE} from '../store/mutations-type'
export default {
	data() {
		return {
      emailCode:'',
			showPwd: false,
			computeTime: 0,
			code: '',
			registerList: {
				userName: '',
				password: '',
				email: ''
			},
			password:'',
			loginList: {
				userName: '',
        password: '',
        captcha:''
			}
		}
	},

	components: {},

	computed: {
		rightEmail() {
			return /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(this.registerList.email)
    },
    ...mapState(['loginWay','usercode'])
	},

	mounted() {
    this.getCaptcha()
  },
	created() {},
	methods: {
		async login() {
			// 注册
			if (this.loginWay) {
				const { registerList } = this
				if (!this.rightEmail) {
					return this.$message.error('邮箱不正确')
				} else if (registerList.userName === '') {
					return this.$message.error('请输入用户名')
				} else if (registerList.password === '') {
					return this.$message.error('请输入密码')
				} else if (registerList.password !== this.password) {
					return this.$message.error('密码不一致')
				}
        if(this.emailCode===this.code||this.userCode===this.code){
          const {data:resultregister} = await this.$api.login.register(registerList)
          if(resultregister.code===1){
            let loginWay = true
						this.$store.commit(SET_LOGINWAY, loginWay)
						this.$message.success('注册成功！')
						this.$refs.formRef.clearValidate()
						this.loginWay=false
          }else{
            this.registerList.userName=''
            this.registerList.password=''
            this.registerList.email=''
            return this.$message.error(resultregister.message)
          }
        }else{
          return this.$message.error('激活码不正确！')
        }
			}else{
        // 登录
        if(this.loginList.captcha===''){
					return this.$message.error('请输入验证码')
				} else if (this.loginList.userName === '') {
					return this.$message.error('请输入用户名')
				} else if (this.loginList.password === '') {
					return this.$message.error('请输入密码')
        }else if (localStorage.getItem('user')){
					return this.$message.error('已登录！')
				}
        const {data:resultLogin} = await this.$api.login.login(this.loginList)
        if(resultLogin.code===1){
					const response=resultLogin.response
          localStorage.setItem('user', JSON.stringify(response))
          localStorage.setItem('token', response.token)
					if(response.role===false){
						localStorage.setItem('isRole',1)
					}else{
						localStorage.setItem('isRole',0)
					}
          this.$store.dispatch('recordUser',response)
					this.$message.success(resultLogin.message)
          this.$router.replace('/')
        }else{
          this.getCaptcha()
          this.$message.error(resultLogin.message)
        }
      }
      if(this.computeTime) {
        this.computeTime = 0
        clearInterval(this.intervalId)
        this.intervalId = undefined
      }
		},
		async getCode() {
			if (!this.computeTime){
				this.computeTime = 30
				this.intervalId = setInterval(() => {
					this.computeTime--
					if (this.computeTime <= 0) {
						clearInterval(this.intervalId)
					}
				}, 1000)
				// 发送ajax请求(向指定邮箱发送验证码)
				const { data: result } = await this.$api.login.sendMimeMail(
					this.registerList.userName,this.registerList.email
        )
        // console.log(result)
				if (result.code === 1) {
          this.emailCode=result.message
          this.$store.commit(USERCODE,result.message)
					// 显示提示
					this.$message.success('激活码发送成功！')
					// 停止计时
					if (this.computeTime) {
						this.computeTime = 0
						clearInterval(this.intervalId)
						this.intervalId = undefined
					}
				}else{
          return this.$message.error(result.message)
        }
			}
		},
		getCaptcha() {
      this.$refs.captcha.src = '/api/user/verification'
    },
    chance(value) {
      let loginWay
			if (value === 1) {
				loginWay = true
				this.$store.commit(SET_LOGINWAY, loginWay)
			} else if (value === 2) {
				loginWay = false
				this.$store.commit(SET_LOGINWAY, loginWay)
			}
		}
	}
}
</script>
<style lang='stylus' scoped>
.situation
	background-color rgba(255, 255, 255, 0.5)
	padding 0 8.125rem
	.mainHead
		height 3.75rem
		border-bottom 0.0625rem #ccc solid
		overflow hidden
		display flex
		justify-content space-between
		h3
			width 10rem
			border-bottom 0.125rem #117750 solid
			font-size 1.5rem
			color #117750
			line-height 3.625rem
			padding 0 1.25rem 0 2.5rem
		.el-breadcrumb
			width 12.5rem
			font-size 0.9375rem
			line-height 3.75rem
			z-index 4
	.mainBody
		height 39.3125rem
		.login_header_title
			padding-top 2.5rem
			text-align center
			>a
				color #333
				font-size 1.125rem
				padding-bottom 0.25rem
				&:first-child
					margin-right 2.5rem
				&.on
					color #02a774
					font-weight 700
					border-bottom 0.125rem solid #02a774
		.login_content
			display flex
			flex-direction column
			align-items center
			>form
				width 18.75rem
				>div
					display none
					&.on
						display block
					input
						width 100%
						height 100%
						padding-left 0.625rem
						box-sizing border-box
						border 0.0625rem solid #ddd
						border-radius 0.25rem
						outline 0
						font 400 0.875rem Arial
						&:focus
							border 0.0625rem solid #02a774
					.login_message
						position relative
						margin-top 1rem
						height 3rem
						font-size 0.875rem
						background #fff
						.get_verification
							position absolute
							top 50%
							right 0.625rem
							transform translateY(-50%)
							border 0
							color #ccc
							font-size 0.875rem
							background transparent
							&.right_phone
								color black
					.login_verification
						position relative
						margin-top 1rem
						height 3rem
						font-size 0.875rem
						background #fff
						.switch_button
							font-size 0.75rem
							border 0.0625rem solid #ddd
							border-radius 0.5rem
							transition background-color 0.3s, border-color 0.3s
							padding 0 0.375rem
							width 1.875rem
							height 1rem
							line-height 1rem
							color #fff
							position absolute
							top 50%
							right 0.625rem
							transform translateY(-50%)
							&.off
								background #fff
								.switch_text
									float right
									color #ddd
							&.on
								background #02a774
							>.switch_circle
								// transform translateX(1.6875rem)
								position absolute
								top -0.0625rem
								left -0.0625rem
								width 1rem
								height 1rem
								border 0.0625rem solid #ddd
								border-radius 50%
								background #fff
								box-shadow 0 0.125rem 0.25rem 0 rgba(0, 0, 0, 0.1)
								transition transform 0.3s
								&.right
									transform translateX(1.6875rem)
					.login_hint
						margin-top 0.75rem
						color #999
						font-size 0.875rem
						line-height 1.25rem
						>a
							color #02a774
				.login_submit
					display block
					width 100%
					height 2.625rem
					margin-top 1.875rem
					border-radius 0.25rem
					background #4cd96f
					color #fff
					text-align center
					font-size 1rem
					line-height 2.625rem
					border 0
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.situation
		padding 0 2.125rem
		.mainHead
			display flex
			justify-content space-between
			.el-breadcrumb
				width 30%
				font-size .9375rem
				line-height 3.75rem
</style>
