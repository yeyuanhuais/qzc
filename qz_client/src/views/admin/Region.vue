<template>
	<div>
		<!-- 导航 -->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>景点</el-breadcrumb-item>
			<el-breadcrumb-item>景点列表</el-breadcrumb-item>
		</el-breadcrumb>
		<!-- 卡片 -->
		<el-card class="box-card">
			<el-row :gutter="20">
				<el-col :span="4">
					<el-button type="primary" @click="addDialogVisible=true">添加景点</el-button>
				</el-col>
			</el-row>
			<el-table :data="regionList" border stripe>
				<el-table-column type="index" label="#"></el-table-column>
				<el-table-column prop="title" label="标题"></el-table-column>
				<el-table-column prop="createTime" label="时间"></el-table-column>
				<el-table-column prop="content" label="内容" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column prop="source" label="来源"></el-table-column>
				<el-table-column prop="author" label="作者"></el-table-column>
				<el-table-column prop="imagePath" label="图片">
					<template slot-scope="scope">
						<img :src="scope.row.imagePath" style="width:3.75rem;height:3.75rem;"/>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="130px">
					<template slot-scope="scope">
						<el-button type="primary" icon="el-icon-edit" circle @click="showEditDialog(scope.row.id)"></el-button>
						<el-button type="danger" icon="el-icon-delete" circle @click="deleteBox(scope.row)"></el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<el-dialog title="添加景点" :visible.sync="addDialogVisible" width="80%" @close="addDialogClose">
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
					></quill-editor>
				</el-form-item>
				<el-form-item label="来源" prop="source">
					<el-input v-model="addForm.source"></el-input>
				</el-form-item>
				<el-form-item label="作者" prop="author">
					<el-input v-model="addForm.author"></el-input>
				</el-form-item>
				<el-form-item label="摘要" prop="digest">
					<el-input v-model="addForm.digest"></el-input>
				</el-form-item>
				<el-form-item label="封面图片" prop="imagePath">
					<el-upload
						ref="upload"
						action="upload/uploadimage"
						list-type="picture-card"
						:multiple="false"
						:file-list="fileList"
						:limit="num"
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
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="addDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="addRegion">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 编辑 -->
		<el-dialog title="修改文章" :visible.sync="editDialogVisible" width="80%" @close="editDialogClose">
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
					></quill-editor>
				</el-form-item>
				<el-form-item label="来源" prop="source">
					<el-input v-model="editForm.source"></el-input>
				</el-form-item>
				<el-form-item label="作者" prop="author">
					<el-input v-model="editForm.author"></el-input>
				</el-form-item>
				<el-form-item label="摘要" prop="digest">
					<el-input v-model="editForm.digest"></el-input>
				</el-form-item>
				<el-form-item label="封面图片" prop="imagePath">
					<el-upload
						ref="upload"
						action="upload/uploadimage"
						list-type="picture-card"
						:multiple="false"
						:file-list="fileList"
						:limit="num"
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
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="editDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="editRegion">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	data() {
		return {
			regionList: [],
			addDialogVisible: false,
			addForm: {
				title: '',
				content: '',
				source: '',
				digest: '',
				createTime: '',
				author: '',
				imagePath: '',
				imageKey: ''
			},
			FormRules: {
				title: [{ required: true, message: '内容不能为空', trigger: 'blur' }]
			},
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
			editDialogVisible: false,
			editForm: {
				title: '',
				content: '',
				source: '',
				digest: '',
				createTime: '',
				author: '',
				imagePath: '',
				imageKey: ''
			},
			//   上传图片
			num: 1,
			disabled: false,
			fileList: []
		}
	},
	computed: {
		editor() {
			return this.$refs.myTextEditor.quillEditor
		}
	},
	mounted() {
		this.getRegionList()
	},
	methods: {
		async getRegionList() {
			const { data: res } = await this.$api.region.getAll(this.query)
			if (res.code !== 1) {
				return this.$message.error('获取景点列表失败！')
			}
			this.regionList = res.response
		},
		// 添加
		addDialogClose() {
			this.$refs.addFormRef.resetFields()
		},
		addRegion() {
			this.$refs.addFormRef.validate(async valid => {
				if (!valid) return

				const { data: res } = await this.$api.region.add(this.addForm)
				if (res.code !== 1) {
					return this.$message.error('添加景点失败！')
				}
				this.$message.success('添加景点成功！')
				this.addDialogVisible = false
				this.fileList = []
				this.getRegionList()
			})
		},
		// 编辑
		async showEditDialog(id) {
			const { data: res } = await this.$api.region.getById(id)
			if (res.code !== 1) {
				return this.$message.error('查询景点失败！')
			}
			this.editForm = res.response
			this.editDialogVisible = true
		},
		editDialogClose() {
			this.$refs.editFormRef.resetFields()
		},
		editRegion() {
			this.$refs.editFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.region.edit(
					this.editForm
				)
				if (res.code !== 1) {
					return this.$message.error('编辑景点失败！')
				}
				this.$message.success('编辑景点成功！')
				this.editDialogVisible = false
				this.getRegionList()
			})
		},
		// 删除
		async deleteBox(tn) {
			const confirmResult = await this.$confirm(
				'此操作将永久删除该景点, 是否继续?',
				'提示',
				{
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}
			)
				.then(async () => {
					const { data: res } = await this.$api.region.delete(
						tn.id
					)
					if (res.code !== 1) {
						return this.$message.error('删除景点失败！')
					}
					this.getRegionList()
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