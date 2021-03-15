<!--文章-->
<template>
	<div>
		<!-- 导航 -->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>文章</el-breadcrumb-item>
			<el-breadcrumb-item>文章列表</el-breadcrumb-item>
		</el-breadcrumb>
		<!-- 卡片 -->
		<el-card class="box-card">
			<el-row :gutter="20">
				<el-col :span="7">
					<el-input placeholder="请输入内容" v-model="queryInfo.keyword" @clear="getArticleList" clearable>
						<el-button slot="append" icon="el-icon-search" @click="getArticleList"></el-button>
					</el-input>
				</el-col>
				<el-col :span="4">
					<el-button type="primary" @click="addDialogVisible=true">添加文章</el-button>
				</el-col>
			</el-row>
			<el-table
				:data="articleList"
				border
				stripe
				:default-sort="{prop: 'isShow'}"
				@filter-change="handleFilterChange"
			>
				<el-table-column type="index" label="#"></el-table-column>
				<el-table-column prop="title" label="标题"></el-table-column>
				<el-table-column prop="time" label="时间"></el-table-column>
				<el-table-column prop="content" label="内容" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column prop="source" label="来源"></el-table-column>
				<el-table-column label="状态" prop="isShow" sortable>
					<template slot-scope="scope">
						<el-switch v-model="scope.row.isShow" @change="articleStateChanged(scope.row)"></el-switch>
					</template>
				</el-table-column>
				<el-table-column
					prop="className"
					label="分类"
					:filters="[{ text: '民风民俗', value: '1' },
					{ text: '泉州建筑', value: '2' },
					{ text: '闽南方言', value: '3' },
					{ text: '宗教文化', value: '4' }]"
					filter-placement="bottom-end"
				></el-table-column>
				<el-table-column label="操作" width="130px">
					<template slot-scope="scope">
						<el-button type="primary" icon="el-icon-edit" circle @click="showEditDialog(scope.row.id)"></el-button>
						<el-button type="danger" icon="el-icon-delete" circle @click="deleteBox(scope.row)"></el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:current-page.sync="queryInfo.num"
				:page-sizes="[5,10, 20, 30, 40]"
				:page-size="queryInfo.size"
				layout="total,sizes, prev, pager, next"
				:total="count"
			></el-pagination>
		</el-card>
		<!-- 添加游记 -->
		<el-dialog title="添加用户" :visible.sync="addDialogVisible" width="70%" @close="addDialogClose">
			<el-form
				:model="addForm"
				:rules="FormRules"
				ref="addFormRef"
				label-width="100px"
				class="demo-ruleForm"
				enctype="multipart/form-data"
			>
				<el-form-item label="标题" prop="title">
					<el-input v-model="addForm.title"></el-input>
				</el-form-item>
				<el-form-item label="时间" prop="time">
					<el-input v-model="addForm.time"></el-input>
				</el-form-item>
				<el-form-item label="内容" prop="content">
					<quill-editor
						class="editor"
						ref="myTextEditor"
						v-model="addForm.content"
						:options="editorOption"
						@blur="onEditorBlur($event)"
						@focus="onEditorFocus($event)"
						@ready="onEditorReady($event)"
						@change="onEditorChange($event)"
					></quill-editor>
				</el-form-item>
				<el-form-item label="来源" prop="source">
					<el-input v-model="addForm.source"></el-input>
				</el-form-item>
				<el-form-item label="封面图片" prop="imagePath">
					<el-upload
						ref="upload"
						action="upload/uploadimage"
						list-type="picture-card"
						:multiple="false"
						:file-list="fileList"
						:limit="num"
						:onError="uploadError"
						:onSuccess="uploadSuccess"
						:beforeUpload="beforeAvatarUpload"
						:on-exceed="handleExceed"
						:http-request="uploadUrlAdd"
						name="file"
					>
						<i slot="default" class="el-icon-plus"></i>
						<div slot="file" slot-scope="{file}">
							<img class="el-upload-list__item-thumbnail" :src="file.url" alt />
							<span class="el-upload-list__item-actions">
								<span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemoveAdd(file)">
									<i class="el-icon-delete"></i>
								</span>
							</span>
						</div>
					</el-upload>
					<el-input v-model="addForm.imagePath" disabled></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="isShow">
					<el-switch v-model="addForm.isShow" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
				<el-form-item label="分类" prop="className">
					<el-select v-model="addForm.classId" @change="pickRoleNameAdd" placeholder="请选择">
						<el-option v-for="item in classList" :key="item.id" :label="item.className" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="addDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="addArticle">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 编辑 -->
		<el-dialog title="修改文章" :visible.sync="editDialogVisible" width="70%" @close="editDialogClose">
			<el-form
				:model="editForm"
				:rules="FormRules"
				ref="editFormRef"
				label-width="100px"
				class="demo-ruleForm"
				enctype="multipart/form-data"
			>
				<el-form-item label="标题" prop="title">
					<el-input v-model="editForm.title"></el-input>
				</el-form-item>
				<el-form-item label="时间" prop="time">
					<el-input v-model="editForm.time"></el-input>
				</el-form-item>
				<el-form-item label="内容" prop="content">
					<quill-editor
						class="editor"
						ref="myTextEditor"
						v-model="editForm.content"
						:options="editorOption"
						@blur="onEditorBlur($event)"
						@focus="onEditorFocus($event)"
						@ready="onEditorReady($event)"
						@change="onEditorChange($event)"
					></quill-editor>
				</el-form-item>
				<el-form-item label="来源" prop="source">
					<el-input v-model="editForm.source"></el-input>
				</el-form-item>
				<el-form-item label="封面图片" prop="imagePath">
					<img src="editForm.imagePath" alt />
					<el-upload
						ref="upload"
						action="upload/uploadimage"
						list-type="picture-card"
						:multiple="false"
						:file-list="fileList"
						:limit="num"
						:onError="uploadError"
						:onSuccess="uploadSuccess"
						:beforeUpload="beforeAvatarUpload"
						:on-exceed="handleExceed"
						:http-request="uploadUrlEdit"
						name="file"
					>
						<i slot="default" class="el-icon-plus"></i>
						<div slot="file" slot-scope="{file}">
							<img class="el-upload-list__item-thumbnail" :src="file.url" alt />
							<span class="el-upload-list__item-actions">
								<span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemoveEdit(file)">
									<i class="el-icon-delete"></i>
								</span>
							</span>
						</div>
					</el-upload>
					<el-input v-model="editForm.imagePath" disabled></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="isShow">
					<el-switch v-model="editForm.isShow" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
				<el-form-item label="分类" prop="className">
					<el-select v-model="editForm.classId" @change="pickRoleNameEdit" placeholder="请选择">
						<el-option v-for="item in classList" :key="item.id" :label="item.className" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="editDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="editArticle">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	data() {
		return {
			queryInfoClass: {
				keyword: '',
				size: 100,
				num: 1
			},
			queryInfo: {
				keyword: '',
				size: 5,
				num: 1,
				classId:''
			},
			count: 0,
			// 文章感悟列表
			articleList: [],
			//   分类
			classList: [],
			// 添加文章
			addDialogVisible: false,
			addForm: {
				title: '',
				time: '',
				content: '',
				source: '',
				isShow: 0,
				classId: '',
				className: '',
				imagePath: '',
				imageKey: ''
			},
			// 编辑文章
			editDialogVisible: false,
			editForm: {
				title: '',
				time: '',
				content: '',
				source: '',
				isShow: 0,
				className: '',
				imagePath: '',
				imageKey: ''
			},
			FormRules: {
				title: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				time: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				content: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				source: [{ required: true, message: '内容不能为空', trigger: 'blur' }]
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
			dialogImageUrl: '',
			dialogVisible: false,
			disabled: false,
			fileList: []
		}
	},

	components: {},

	computed: {
		editor() {
			return this.$refs.myTextEditor.quillEditor
		}
	},

	created() {
		this.getArticleList()
		this.getClassList()
	},

	mounted() {},

	methods: {
		handleFilterChange(filters){
			let row = null
			let val = null
			// 拷贝filters的值。
			for (const i in filters) {
				row = i // 保存 column-key的值，如果事先没有为column-key赋值，系统会自动生成一个唯一且恒定的名称
				val = filters[i]
			}
			this.queryInfo.classId=val[0]
			this.getArticleList()
			console.log(val[0])
		},
		// 文章列表
		async getArticleList() {
			const { data: res } = await this.$api.article.getAll(this.queryInfo)
			if (res.code !== 1) {
				return this.$message.error('获取文章列表失败！')
			}
			this.articleList = res.response
			this.count = res.count
			console.log(this.articleList)
		},
		handleSizeChange(val) {
			this.queryInfo.size = val
			this.getArticleList()
		},
		handleCurrentChange(val) {
			this.queryInfo.num = val
			this.getArticleList()
		},
		// 分类列表
		async getClassList() {
			const { data: res } = await this.$api.classification.getAll(
				this.queryInfoClass
			)
			if (res.code !== 1) {
				return this.$message.error('获取分类列表失败！')
			}
			this.classList = res.response
		},
		getClassId(id) {
			this.addForm.classId = id
		},
		// 状态转换
		async articleStateChanged(article) {
			const { data: res } = await this.$api.article.articleStateChanged(
				article.id,
				article.isShow
			)
			if (res.code !== 1) {
				article.isShow = !article.isShow
				return this.$message.error('更新文章状态失败！')
			}
			this.$message.success('更新文章状态成功')
		},
		// 添加
		addDialogClose() {
			this.$refs.addFormRef.resetFields()
		},
		addArticle() {
			this.$refs.addFormRef.validate(async valid => {
				if (!valid) return

				const { data: res } = await this.$api.article.add(this.addForm)
				if (res.code !== 1) {
					return this.$message.error('添加文章失败！')
				}
				this.$message.success('添加文章成功！')
				this.addDialogVisible = false
				this.getArticleList()
			})
		},
		// 编辑
		async showEditDialog(id) {
			const { data: res } = await this.$api.article.articleOne(id)
			if (res.code !== 1) {
				return this.$message.error('查询文章失败！')
			}
			this.editForm = res.response
			this.editDialogVisible = true
		},
		editDialogClose() {
			this.$refs.editFormRef.resetFields()
		},
		editArticle() {
			this.$refs.editFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.article.edit(this.editForm)
				if (res.code !== 1) {
					return this.$message.error('编辑文章失败！')
				}
				this.$message.success('编辑文章成功！')
				this.editDialogVisible = false
				this.getArticleList()
			})
		},
		// 删除
		async deleteBox(tn) {
			const confirmResult = await this.$confirm(
				'此操作将永久删除该文章, 是否继续?',
				'提示',
				{
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}
			)
				.then(async () => {
					const { data: res } = await this.$api.article.delete(tn.id)
					if (res.code !== 1) {
						return this.$message.error('删除文章失败！')
					}
					this.getArticleList()
					this.$message({
						type: 'success',
						message: '删除成功!'
					})
				})
				.catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					})
				})
			console.log(confirmResult)
		},
		onEditorBlur(editor) {
			// console.log('editor blur!', editor)
		},
		onEditorFocus(editor) {
			// console.log('editor focus!', editor)
		},
		onEditorReady(editor) {
			// console.log('editor ready!', editor)
		},
		onEditorChange(editor) {},
		// 选择分类
		pickRoleNameAdd(e) {
			let name = {}
			name = this.classList.find(item => {
				return item.id === e
			})
			this.addForm.className = name.className
			console.log(this.addForm)
		},
		pickRoleNameEdit(e) {
			let name = {}
			name = this.classList.find(item => {
				return item.id === e
			})
			this.editForm.className = name.className
		},
		// 图片上传
		async uploadUrlAdd(file) {
			let fd = new FormData()
			fd.append('file', file.file)
			const { data: res } = await this.$api.upload.uploadimage(fd, {
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			})
			this.addForm.imagePath = res.response.url
			this.addForm.imageKey = res.response.fileName
		},
		async uploadUrlEdit(file) {
			let fd = new FormData()
			fd.append('file', file.file)
			const { data: res } = await this.$api.upload.uploadimage(fd, {
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			})
			this.editForm.imagePath = res.response.url
			this.editForm.imageKey = res.response.fileName
		},
		async handleRemoveAdd(file) {
			if (file.status === 'success') {
				const { data: res } = await this.$api.upload.delete(
					this.addForm.imageKey
				)
				if (res.code !== 1) {
					return this.$message.error('删除图片失败！')
				}
				this.fileList = []
				this.addForm.imageKey = ''
				return this.$message.success('删除图片成功！')
			}
		},
		async handleRemoveEdit(file) {
			if (file.status === 'success') {
				const { data: res } = await this.$api.upload.delete(
					this.editForm.imageKey
				)
				if (res.code !== 1) {
					return this.$message.error('删除图片失败！')
				}
				this.fileList = []
				this.editForm.imageKey = ''
				return this.$message.success('删除图片成功！')
			}
		},
		handlePictureCardPreview(file) {
			this.dialogImageUrl = file.url
			this.dialogVisible = true
		},
		handleDownload(file) {
			console.log(file)
		},
		// 上传成功后的回调
		uploadSuccess(response, file, fileList) {
			console.log('上传文件', response)
		},
		// 上传错误
		uploadError(response, file, fileList) {
			console.log('上传失败，请重试！')
		},
		// 上传前对文件的大小的判断
		beforeAvatarUpload(file) {
			const isLt2M = file.size / 1024 / 1024 < 10
			if (
				file.type !== 'image/jpg' &&
				file.type !== 'image/jpeg' &&
				file.type !== 'image/png' &&
				file.type !== 'image/gif'
			) {
				this.$message.error('只支持jpg、png、gif格式的图片！')
				return false
			}
			if (!isLt2M) {
				this.$message.error('上传模板大小不能超过 10MB!')
			}
			return isLt2M
		},
		// 提醒超过限制数
		handleExceed(files, fileList) {
			this.$message.warning(
				`当前限制选择 ${this.num}个文件，本次选择了 ${files.length +
					fileList.length -
					1} 个文件`
			)
		}
	}
}
</script>
<style lang='stylus' scoped>
.quill-editor:not(.bubble) .ql-container, .quill-editor:not(.bubble) .ql-container .ql-editor
	height 30rem
	padding-bottom 1rem
.quill-editor
	width 100%
	float left
</style>