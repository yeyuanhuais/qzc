<!--  -->
<template>
	<div class="page">
		<div class="mainHead">
			<h3>用户中心</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>用户</el-breadcrumb-item>
				<el-breadcrumb-item>用户管理中心</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<el-tabs tab-position="left" style="height: 100%;">
				<el-tab-pane label="个人信息" v-if="user">
					<el-form ref="userFormRef" :model="userInfo" label-width="8.75rem">
						<el-form-item class="adatar">
							<el-upload
								class="avatar-uploader"
								action="/api/upload/uploadimage"
								:show-file-list="false"
								:on-success="handleAvatarSuccess"
								:on-remove="handleRemove"
								:before-upload="beforeAvatarUpload"
							>
								<img v-if="this.userInfo.imagePath" :src="this.userInfo.imagePath" class="avatar" />
								<span v-if="this.userInfo.imagePath" class="el-upload-action" @click.stop="handleRemove()">
									<i class="el-icon-delete"></i>
								</span>
								<i v-else class="el-icon-upload2 avatar-uploader-icon" stop></i>
							</el-upload>
						</el-form-item>
						<el-form-item label="用户名" prop="userName">
							<el-input v-model="userInfo.userName"></el-input>
						</el-form-item>
						<el-form-item label="邮箱" prop="email">
							<el-input v-model="userInfo.email"></el-input>
						</el-form-item>
						<el-form-item label="新密码" prop="password">
							<el-input v-model="userInfo.password"></el-input>
						</el-form-item>
					</el-form>
					<el-button class="info" type="primary" round @click="userInfoChange">提交修改</el-button>
				</el-tab-pane>
				<el-tab-pane label="发布文章">
						<el-form
							:model="articleForm"
							:rules="articleFormRules"
							ref="articleFormRef"
							label-width="6.25rem"
							class="demo-ruleForm"
							enctype="multipart/form-data"
						>
							<el-form-item label="标题" prop="title">
								<el-input v-model="articleForm.title"></el-input>
							</el-form-item>
							<el-form-item label="内容" prop="content">
								<quill-editor
									class="editor"
									ref="myTextEditor"
									v-model="articleForm.content"
									:options="editorOption"
								></quill-editor>
							</el-form-item>
							<el-form-item label="封面图片" prop="imagePath">
								<Upload @getPic="getArticleImage" />
								<el-input v-model="articleForm.imagePath" disabled></el-input>
							</el-form-item>
							<el-form-item label="分类" prop="className">
								<el-select
									ref="selectArt"
									v-model="articleForm.classId"
									@change="pickClassName"
									placeholder="请选择"
								>
									<el-option
										v-for="item in classList"
										:key="item.id"
										:label="item.className"
										:value="item.id"
									></el-option>
								</el-select>
							</el-form-item>
						</el-form>
						<span class="dialog-footer">
							<el-button @click="this.$refs.articleFormRef.resetFields()">取 消</el-button>
							<el-button type="primary" @click="addArticle">确 定</el-button>
						</span>
				</el-tab-pane>
				<el-tab-pane label="发布游记">
					<el-form
						:model="travelNoteForm"
						:rules="travelNoteFormRules"
						ref="travelNoteFormRef"
						label-width="6.25rem"
						class="demo-ruleForm"
						enctype="multipart/form-data"
					>
						<el-form-item label="标题" prop="title">
							<el-input v-model="travelNoteForm.title"></el-input>
						</el-form-item>
						<el-form-item label="内容" prop="content">
							<el-input v-model="travelNoteForm.content"></el-input>
						</el-form-item>
						<el-form-item label="图片地址" prop="imagePath">
							<Upload @getPic="getTravelNoteImage" />
							<el-input v-model="travelNoteForm.imagePath" disabled></el-input>
						</el-form-item>
					</el-form>
					<span class="dialog-footer">
						<el-button @click="this.$refs.travelNoteFormRef.resetFields()">取 消</el-button>
						<el-button type="primary" @click="addTravelNote">确 定</el-button>
					</span>
				</el-tab-pane>
			</el-tabs>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex'
import Upload from '../components/Upload'
export default {
	data() {
		return {
			params: {
				keyword: '',
				num: 1,
				size: 30
			},
			userInfo: {
				id: '',
				userName: '',
				email: '',
				password: '',
				imageKey: '',
				imagePath: ''
			},
			// 添加文章
			articleForm: {
				title: '',
				time: new Date(),
				content: '',
				source: '',
				isShow: 0,
				classId: '',
				className: '',
				imagePath: '',
				imageKey: ''
			},
			articleFormRules: {
				title: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				content: [{ required: true, message: '内容不能为空', trigger: 'blur' }]
			},
			// 添加游记
			travelNoteForm: {
				title: '',
				time: new Date(),
				content: '',
				source: '',
				isShow: 0,
				classId: '',
				className: '',
				imagePath: '',
				imageKey: ''
			},
			travelNoteFormRules: {
				title: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				content: [{ required: true, message: '内容不能为空', trigger: 'blur' }]
			},
			//   富文本
			editorOption: {
				modules: {
					toolbar: [
						[{ size: ['small', false, 'large'] }],
						['bold', 'italic'],
						[{ list: 'ordered' }, { list: 'bullet' }],
						['link', 'image']
					],
					history: {
						delay: 1000,
						maxStack: 50,
						userOnly: false
					},
					imageDrop: true,
					imageResize: {
						displayStyles: {
							backgroundColor: 'black',
							border: 'none',
							color: 'white'
						},
						modules: ['Resize', 'DisplaySize', 'Toolbar']
					}
				}
			},
			//   上传图片
			num: 1,
			fileList: [],
			disabled: false,
			count: 0,
			classList: []
		}
	},
	components: {
		Upload
	},
	computed: {
		editor() {
			return this.$refs.myTextEditor.quillEditor
		},
		...mapState(['user'])
	},
	mounted() {
		this.getClassList()
		this.userInfo.id = this.user.id
		this.userInfo.userName = this.user.userName
		this.userInfo.email = this.user.email
		this.userInfo.imagePath = this.user.imagePath
		this.userInfo.imageKey = this.user.imageKey
		this.articleForm.source = this.user.userName
		this.travelNoteForm.source = this.user.userName
	},

	methods: {
		// 移除图片
		handleRemove() {
			this.userInfo.imagePath = ''
		},
		// 上传成功回调
		handleAvatarSuccess(res, file) {
			if (res.code === 1) {
				this.userInfo.imagePath = res.response.url
				this.userInfo.imageKey = res.response.fileName
				return this.$message.success('头像上传成功')
			} else {
				return this.$message.error('头像上传失败')
			}
		},
		// 上传前格式和图片大小限制
		beforeAvatarUpload(file) {
			const type =
				file.type === 'image/jpeg' || 'image/jpg' || 'image/webp' || 'image/png'
			const isLt2M = file.size / 1024 / 1024 < 2
			if (!type) {
				this.$message.error('图片格式不正确!(只能包含jpg，png，webp，JPEG)')
			}
			if (!isLt2M) {
				this.$message.error('上传图片大小不能超过 2MB!')
			}
			return type && isLt2M
		},
		// 分类列表
		async getClassList() {
			const { data: res } = await this.$api.classification.getAll(this.params)
			if (res.code !== 1) {
				return this.$message.error('获取分类列表失败！')
			}
			this.classList = res.response
		},
		// 修改用户信息
		async userInfoChange() {
			console.log(this.userInfo)
			const { data: res } = await this.$api.user.edit(this.userInfo)
			if (res.code === 1) {
				// this.userInfo.id = this.user.id
				// this.userInfo.userName = this.user.userName
				// this.userInfo.email = this.user.email
				// this.userInfo.imagePath=this.user.imagePath
				const { data: resultLogin } = await this.$api.user.getById(
					this.userInfo.id
				)
				if (resultLogin.code === 1) {
					const response = resultLogin.response
					localStorage.setItem('user', JSON.stringify(response))
					return this.$message.success('修改成功')
				}
			} else {
				return this.$message.error(res.message)
			}
		},
		// 添加文章
		addArticle() {
			this.$refs.articleFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.article.add(this.articleForm)
				if (res.code !== 1) {
					return this.$message.error('添加文章失败！')
				}
				this.$message.success('添加文章成功！待管理员审核')
				this.$refs.articleFormRef.resetFields()
				this.articleForm.className = ''
				this.articleForm.classId = ''
			})
		},
		// 获取分类名
		pickClassName(e) {
			let name = {}
			name = this.classList.find(item => {
				return item.id === e
			})
			this.articleForm.className = name.className
		},
		// 添加游记
		addTravelNote() {
			this.$refs.travelNoteFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.travelNotes.add(
					this.travelNoteForm
				)
				if (res.code !== 1) {
					return this.$message.error('添加游记失败！')
				}
				this.$message.success('添加游记成功！待管理员审核')
				this.$refs.travelNoteFormRef.resetFields()
				this.fileList = []
			})
		},
		load() {
			this.count += 3
		},
		getArticleImage(data) {
			console.log(data)
			this.articleForm.imageKey = data.fileName
			this.articleForm.imagePath = data.url
		},
		getTravelNoteImage(data) {
			this.travelNoteForm.imageKey = data.fileName
			this.travelNoteForm.imagePath = data.url
		}
	},
	watch: {
		user(value) {
			this.$nextTick(() => {
				this.userInfo.id = this.user.id
				this.userInfo.userName = this.user.userName
				this.userInfo.email = this.user.email
				this.userInfo.imagePath = this.user.imagePath
				this.userInfo.imageKey = this.user.imageKey
				this.articleForm.source = this.user.userName
				this.travelNoteForm.source = this.user.userName
			})
		}
	}
}
</script>
<style lang='stylus' scoped>
.page
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
		margin 1.875rem 1.875rem 0 1.875rem
		display flex
		width 95%
		background-color rgba(255, 255, 255, 0.8)
		.el-tabs
			height 99%
			width 90%
			margin 1.25rem
			padding 1.25rem
			.el-tabs--left
			.el-tabs__header
			.is-left
				width 12%
			.el-tab-pane
				.adatar
					display flex
					justify-content center
					margin-bottom .625rem
					.avatar-uploader
						width 7.5rem
						height 7.5rem
						border-radius 50%
						cursor pointer
						position relative
						overflow hidden
						background url('../assets/images/admin.jpg') no-repeat
						background-size 100% 100%
						.avatar-uploader-icon
							font-size 0
							color #fff
							width 7.5rem
							height 7.5rem
							line-height 7.5rem
							text-align center
							&:hover
								font-size 1.75rem
								background-color rgba(0, 0, 0, 0.3)
						.avatar
							position relative
							width 7.5rem
							height 7.5rem
							display block
						.el-upload-action
							position absolute
							top 0
							left 0
							display block
							width 100%
							height 100%
							font-size 0
							color #fff
							text-align center
							line-height 7.5rem
							&:hover
								font-size 1.25rem
								background-color #000
								background-color rgba(0, 0, 0, 0.3)
				.info
					position relative
					left 5.625rem
				.dialog-footer
					margin 10% 0 0 8%
				// .infinite-list
				// 	height 34.375rem
				// 	padding-right 1.25rem
				// 	padding-bottom .625rem
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.page
		padding 0 2.125rem
		.mainHead
			display flex
			justify-content space-around
			.el-breadcrumb
				font-size .9375rem
				line-height 3.75rem
		.mainBody
			margin 1.875rem .875rem 0 .875rem
			width 95%
</style>