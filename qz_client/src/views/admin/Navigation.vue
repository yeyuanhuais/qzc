<!--导航栏-->
<template>
	<div>
		<!-- 导航 -->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>导航栏</el-breadcrumb-item>
			<el-breadcrumb-item>导航栏列表</el-breadcrumb-item>
		</el-breadcrumb>
		<!-- 卡片 -->
		<el-card class="box-card">
			<el-row :gutter="20">
				<el-col :span="4">
					<el-button type="primary" @click="addDialogVisible=true">添加导航栏</el-button>
				</el-col>
			</el-row>
			<el-table :data="navigationList" border stripe>
				<el-table-column type="index" label="#"></el-table-column>
				<el-table-column prop="menuName" label="名称"></el-table-column>
				<el-table-column prop="icon" label="图标"></el-table-column>
				<el-table-column prop="url" label="跳转地址"></el-table-column>
				<el-table-column prop="sort" label="排序"></el-table-column>
				<el-table-column label="状态">
					<template slot-scope="scope">
						<el-switch v-model="scope.row.isShow" @change="navigationStateChanged(scope.row)"></el-switch>
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
				:current-page.sync="query.num"
				:page-sizes="[5,10, 20, 30, 40]"
				:page-size="query.size"
				layout="total,sizes, prev, pager, next"
				:total="count"
			></el-pagination>
		</el-card>
		<!-- 添加 -->
		<el-dialog title="添加导航栏" :visible.sync="addDialogVisible" width="50%" @close="addDialogClose">
			<el-form
				:model="addForm"
				:rules="FormRules"
				ref="addFormRef"
				label-width="100px"
				class="demo-ruleForm"
				enctype="multipart/form-data"
			>
				<el-form-item label="名称" prop="menuName">
					<el-input v-model="addForm.menuName"></el-input>
				</el-form-item>
				<el-form-item label="图标" prop="icon">
					<el-input v-model="addForm.icon"></el-input>
				</el-form-item>
				<el-form-item label="跳转地址" prop="url">
					<el-input v-model="addForm.url"></el-input>
				</el-form-item>
				<el-form-item label="排序" prop="sort">
					<el-input v-model="addForm.sort"></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="isShow">
					<el-switch v-model="addForm.isShow" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="addDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="addNavigation">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 编辑 -->
		<el-dialog title="修改导航栏" :visible.sync="editDialogVisible" width="50%" @close="editDialogClose">
			<el-form
				:model="editForm"
				:rules="FormRules"
				ref="editFormRef"
				label-width="100px"
				class="demo-ruleForm"
				enctype="multipart/form-data"
			>
				<el-form-item label="名称" prop="menuName">
					<el-input v-model="editForm.menuName"></el-input>
				</el-form-item>
				<el-form-item label="图标" prop="icon">
					<el-input v-model="editForm.icon"></el-input>
				</el-form-item>
				<el-form-item label="跳转地址" prop="url">
					<el-input v-model="editForm.url"></el-input>
				</el-form-item>
				<el-form-item label="排序" prop="sort">
					<el-input v-model="editForm.sort"></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="isShow">
					<el-switch v-model="editForm.isShow" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="editDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="editNavigation">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	data() {
		return {
			query:{
				keyword:'',
				size:5,
				num:1
			},
			count:0,
			// 导航栏感悟列表
			navigationList: [],
			// 添加导航栏
			addDialogVisible: false,
			addForm: {
				menuName: '',
				icon: '',
				url: '',
				sort: '',
				isShow: 0
			},
			// 编辑导航栏
			editDialogVisible: false,
			editForm: {
				menuName: '',
				icon: '',
				url: '',
				sort: '',
				isShow: 0
			},
			FormRules: {
				menuName: [
					{ required: true, message: '内容不能为空', trigger: 'blur' }
				],
				icon: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				url: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
				sort: [{ required: true, message: '内容不能为空', trigger: 'blur' }]
			}
		}
	},

	components: {},

	computed: {},

	created() {
		this.getNavigationList()
	},

	mounted() {},

	methods: {
		// 导航栏列表
		async getNavigationList() {
			const { data: res } = await this.$api.navigation.navigationList(this.query)
			if (res.code !== 1) {
				return this.$message.error('获取导航栏列表失败！')
			}
			this.navigationList = res.response
			this.count=res.count
		},
		handleSizeChange(val) {
			this.query.size = val
			this.getNavigationList()
		},
		handleCurrentChange(val) {
			this.query.num = val
			this.getNavigationList()
		},
		// 状态转换
		async navigationStateChanged(navigation) {
			const { data: res } = await this.$api.navigation.navigationStateChanged(
				navigation.id,navigation.isShow
			)
			if (res.code !== 1) {
				navigation.isShow = !navigation.isShow
				return this.$message.error('更新导航栏状态失败！')
			}
			this.$message.success('更新导航栏状态成功')
		},
		// 添加
		addDialogClose() {
			this.$refs.addFormRef.resetFields()
		},
		addNavigation() {
			this.$refs.addFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.navigation.add(
					this.addForm
				)
				if (res.code !== 1) {
					return this.$message.error('添加导航栏失败！')
				}
				console.log(res)
				this.$message.success('添加导航栏成功！')
				this.addDialogVisible = false
				this.getNavigationList()
			})
		},
		// 编辑
		async showEditDialog(id) {
			const { data: res } = await this.$api.navigation.getById(id)
			if (res.code !== 1) {
				return this.$message.error('查询导航栏失败！')
			}
			this.editForm = res.response
			this.editDialogVisible = true
		},
		editDialogClose() {
			this.$refs.editFormRef.resetFields()
		},
		editNavigation() {
			this.$refs.editFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.navigation.edit(
					this.editForm
				)
				if (res.code !== 1) {
					return this.$message.error('编辑导航栏失败！')
				}
				console.log(res)
				this.$message.success('编辑导航栏成功！')
				this.editDialogVisible = false
				this.getNavigationList()
			})
		},
		// 删除
		async deleteBox(tn) {
			const confirmResult = await this.$confirm(
				'此操作将永久删除该导航栏, 是否继续?',
				'提示',
				{
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}
			)
				.then(async () => {
					const { data: res } = await this.$api.navigation.delete(
						tn.id
					)
					if (res.code !== 1) {
						return this.$message.error('删除导航栏失败！')
					}
					this.getNavigationList()
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
		}
	}
}
</script>
<style lang='stylus' scoped></style>