<!--  -->
<template>
	<div>
		<!-- 导航 -->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>游记感悟</el-breadcrumb-item>
			<el-breadcrumb-item>游记感悟列表</el-breadcrumb-item>
		</el-breadcrumb>
		<!-- 卡片 -->
		<el-card class="box-card">
			<el-row :gutter="20">
				<el-col :span="7">
					<el-input
						placeholder="请输入内容"
						v-model="queryInfo.keyword"
						@clear="getTravelNoteList"
						clearable
					>
						<el-button slot="append" icon="el-icon-search" @click="getTravelNoteList"></el-button>
					</el-input>
				</el-col>
				<el-col :span="4">
					<el-button type="primary" @click="addDialogVisible=true">添加游记</el-button>
				</el-col>
			</el-row>
			<el-table :data="travelNoteList" border stripe  :default-sort = "{prop: 'isShow', order: 'descending'}">
				<el-table-column type="index" label="#"></el-table-column>
				<el-table-column prop="title" label="标题" width="110px"></el-table-column>
				<el-table-column prop="time" label="时间" width="140px"></el-table-column>
				<el-table-column prop="content" label="内容" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column prop="imagePath" label="图片地址" width="150px" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column prop="source" label="来源" width="150px" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column label="状态" width="65px" prop="isShow" sortable>
					<template slot-scope="scope">
						<el-switch v-model="scope.row.isShow" @change="travelNotesStateChanged(scope.row)"></el-switch>
					</template>
				</el-table-column>
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
		<el-dialog title="添加游记" :visible.sync="addDialogVisible" width="50%" @close="addDialogClose">
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
					<el-input v-model="addForm.content"></el-input>
				</el-form-item>
				<el-form-item label="图片地址" prop="image_path">
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
				<el-form-item label="来源" prop="source">
					<el-input v-model="addForm.source"></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="isShow">
					<el-switch v-model="addForm.isShow" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="addDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="addTravelNote">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 编辑 -->
		<el-dialog title="修改游记" :visible.sync="editDialogVisible" width="50%" @close="editDialogClose">
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
					<el-input v-model="editForm.content"></el-input>
				</el-form-item>
				<el-form-item label="图片地址" prop="image_path">
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
								<span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
									<i class="el-icon-delete"></i>
								</span>
							</span>
						</div>
					</el-upload>
					<el-input v-model="editForm.imagePath" disabled></el-input>
				</el-form-item>
				<el-form-item label="来源" prop="source">
					<el-input v-model="editForm.source"></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="isShow">
					<el-switch v-model="editForm.isShow" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="editDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="editTravelNote">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	data() {
		return {
			queryInfo: {
				keyword: '',
				size:5,
				num:1
			},
			count:0,
			// 更新状态
			travelNotes: {},
			// 游记感悟列表
			travelNoteList: [],
			// 添加游记
			addDialogVisible: false,
			addForm: {
				title: '',
				time: '',
				imagePath: '',
				content: '',
				source: '',
				isShow: 0,
				imageKey: ''
			},
			// 编辑游记
			editDialogVisible: false,
			editForm: {
				title: '',
				time: '',
				imagePath: '',
				content: '',
				source: '',
				isShow: 0,
				imageKey: ''
			},
			FormRules: {
				title: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				time: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				imagePath: [
					{ required: true, message: '内容不能为空', trigger: 'blur' }
				],
				content: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				source: [{ required: true, message: '内容不能为空', trigger: 'blur' }]
			},
			//   上传图片
			num: 1,
			disabled: false,
			fileList: []
		}
	},
	created() {
		this.getTravelNoteList()
	},
	methods: {
		// 游记感悟列表
		async getTravelNoteList() {
			const { data: res } = await this.$api.travelNotes.travelNotesList(
				this.queryInfo
			)
			if (res.code !== 1) {
				return this.$message.error('获取游记列表失败！')
			}
			this.travelNoteList = res.response
			this.count=res.count
		},
		handleSizeChange(val) {
			this.queryInfo.size = val
			this.getTravelNoteList()
		},
		handleCurrentChange(val) {
			this.queryInfo.num = val
			this.getTravelNoteList()
		},
		// 状态转换
		async travelNotesStateChanged(travelNotes) {
			const { data: res } = await this.$api.travelNotes.travelNotesStateChanged(
				travelNotes.id,travelNotes.isShow
			)
			if (res.code !== 1) {
				travelNotes.isShow = !travelNotes.isShow
				return this.$message.error('更新游记状态失败！')
			}
			this.$message.success('更新游记状态成功')
		},
		// 添加
		addDialogClose() {
			this.$refs.addFormRef.resetFields()
			this.$refs.upload.clearFiles()
		},
		addTravelNote() {
			this.$refs.addFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.travelNotes.add(
					this.addForm
				)
				if (res.code !== 1) {
					return this.$message.error('添加游记失败！')
				}
				this.$message.success('添加游记成功！')
				this.addDialogVisible = false
				this.getTravelNoteList()
			})
		},
		// 编辑
		async showEditDialog(id) {
			const { data: res } = await this.$api.travelNotes.getById(id)
			if (res.code !== 1) {
				return this.$message.error('查询游记失败！')
			}
			this.editForm = res.response
			this.editDialogVisible = true
		},
		editDialogClose() {
			this.$refs.editFormRef.resetFields()
			this.$refs.upload.clearFiles()
		},
		editTravelNote() {
			this.$refs.editFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.travelNotes.edit(
					this.editForm
				)
				if (res.code !== 1) {
					return this.$message.error('编辑游记失败！')
				}
				// console.log(res)
				this.$message.success('编辑游记成功！')
				this.editDialogVisible = false
				this.getTravelNoteList()
			})
		},
		// 删除
		deleteBox(tn) {
			const confirmResult = this.$confirm(
				'此操作将永久删除该游记, 是否继续?',
				'提示',
				{
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}
			)
				.then(async () => {
					const { data: res } = await this.$api.travelNotes.delete(tn.id)
					if (res.code !== 1) {
						return this.$message.error('删除游记失败！')
					}
					this.getTravelNoteList()
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
			// console.log(confirmResult)
		},
		// 图片上传
		async uploadUrlAdd(file) {
			console.log('1111111111')
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
				const { data: res } = await this.$api.upload.delete(this.addForm.imageKey)
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
				const { data: res } = await this.$api.upload.delete(this.editForm.imageKey)
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
			// console.log(file)
		},
		// 上传成功后的回调
		uploadSuccess(response, file, fileList) {
			// console.log('上传文件', response)
		},
		// 上传错误
		uploadError(response, file, fileList) {
			// console.log('上传失败，请重试！')
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
				return false
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
<style lang='stylus' scoped></style>
