<!--用户-->
<template>
	<div>
		<!-- 导航 -->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>用户</el-breadcrumb-item>
			<el-breadcrumb-item>用户列表</el-breadcrumb-item>
		</el-breadcrumb>
		<!-- 卡片 -->
		<el-card class="box-card">
			<el-row :gutter="20">
				<el-col :span="4">
					<el-button type="primary" @click="addDialogVisible=true">添加用户</el-button>
				</el-col>
			</el-row>
			<el-table :data="userList" border stripe>
				<el-table-column type="index" label="#"></el-table-column>
				<el-table-column prop="userName" label="用户名"></el-table-column>
				<el-table-column prop="email" label="邮箱"></el-table-column>
				<el-table-column prop="createTime" label="创建时间"></el-table-column>
				<el-table-column label="角色">
					<template slot-scope="scope">
						<el-switch v-model="scope.row.role" @change="userRoleChanged(scope.row)"></el-switch>
					</template>
				</el-table-column>
				<el-table-column label="状态">
					<template slot-scope="scope">
						<el-switch v-model="scope.row.status" @change="userStateChanged(scope.row)"></el-switch>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="200px">
					<template slot-scope="scope">
						<el-button type="primary" icon="el-icon-edit" circle @click="showEditDialog(scope.row.id)"></el-button>
						<el-button type="danger" icon="el-icon-delete" circle @click="deleteBox(scope.row)"></el-button>
						<el-tooltip class="item" effect="dark" content="重置密码" placement="top">
							<el-button type="info" icon="el-icon-message" circle @click="resetPassword(scope.row)"></el-button>
						</el-tooltip>
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
		<el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClose">
			<el-form
				:model="addForm"
				:rules="FormRules"
				ref="addFormRef"
				label-width="100px"
				class="demo-ruleForm"
				enctype="multipart/form-data"
			>
				<el-form-item label="用户名" prop="userName">
					<el-input v-model="addForm.userName"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="addForm.email"></el-input>
				</el-form-item>
				<el-form-item label="角色" prop="role">
					<el-switch v-model="addForm.role" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
				<el-form-item label="状态" prop="status">
					<el-switch v-model="addForm.status" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="addDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="addUser">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 编辑 -->
		<el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClose">
			<el-form
				:model="editForm"
				:rules="FormRules"
				ref="editFormRef"
				label-width="100px"
				class="demo-ruleForm"
				enctype="multipart/form-data"
			>
				<el-form-item label="用户名" prop="userName">
					<el-input v-model="editForm.userName"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="editForm.email"></el-input>
				</el-form-item>
				<el-form-item label="角色" prop="role">
					<el-switch v-model="editForm.role" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
				<el-form-item label="状态" prop="status">
					<el-switch v-model="editForm.status" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="editDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="editUser">确 定</el-button>
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
			// 用户列表
			userList: [],
			// 添加用户
			addDialogVisible: false,
			addForm: {
				userName: '',
				email: '',
				role:0,
				status: 0
			},
			// 编辑用户
			editDialogVisible: false,
			editForm: {
				userName: '',
				email: '',
				role:0,
				status: 0
			},
			FormRules: {
				userName: [
					{ required: true, message: '内容不能为空', trigger: 'blur' }
				],
				email: [{ required: true, message: '内容不能为空', trigger: 'blur' }]
			}
		}
	},
	created() {
		this.getUserList()
	},
	methods: {
		// 用户列表
		async getUserList() {
			const { data: res } = await this.$api.user.getAll(this.query)
			console.log(res)
			if (res.code !== 1) {
				return this.$message.error('获取用户列表失败！')
			}
			this.userList = res.response
			this.count=res.count
		},
		handleSizeChange(val) {
			this.query.size = val
			this.getUserList()
		},
		handleCurrentChange(val) {
			this.query.num = val
			this.getUserList()
		},
		// 状态转换
		async userStateChanged(user) {
			const { data: res } = await this.$api.user.userStateChanged(
				user.id,user.status
			)
			if (res.code !== 1) {
				user.status = !user.status
				return this.$message.error('更新用户状态失败！')
			}
			this.$message.success('更新用户状态成功')
		},
		async userRoleChanged(user){
			const { data: res } = await this.$api.user.userRoleChanged(
				user.id,user.role
			)
			if (res.code !== 1) {
				user.role = !user.role
				return this.$message.error('更新用户角色失败！')
			}
			this.$message.success('更新用户角色成功')
		},
		async resetPassword(user){
			const { data: res } = await this.$api.user.resetPassword(
				user.id
			)
			if (res.code !== 1) {
				user.role = !user.role
				return this.$message.error('重置密码失败！')
			}
			this.getUserList()
			this.$message.success('重置密码成功')
		},
		// 添加
		addDialogClose() {
			this.$refs.addFormRef.resetFields()
		},
		addUser() {
			this.$refs.addFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.user.add(
					this.addForm
				)
				if (res.code !== 1) {
					return this.$message.error('添加用户失败！')
				}
				console.log(res)
				this.$message.success('添加用户成功！')
				this.addDialogVisible = false
				this.getUserList()
			})
		},
		// 编辑
		async showEditDialog(id) {
			const { data: res } = await this.$api.user.getById(id)
			if (res.code !== 1) {
				return this.$message.error('查询用户失败！')
			}
			this.editForm = res.response
			this.editDialogVisible = true
		},
		editDialogClose() {
			this.$refs.editFormRef.resetFields()
		},
		editUser() {
			this.$refs.editFormRef.validate(async valid => {
				if (!valid) return
				const { data: res } = await this.$api.user.edit(
					this.editForm
				)
				if (res.code !== 1) {
					return this.$message.error('编辑用户失败！')
				}
				console.log(res)
				this.$message.success('编辑用户成功！')
				this.editDialogVisible = false
				this.getUserList()
			})
		},
		// 删除
		async deleteBox(tn) {
			const confirmResult = await this.$confirm(
				'此操作将永久删除该用户, 是否继续?',
				'提示',
				{
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}
			)
				.then(async () => {
					const { data: res } = await this.$api.user.delete(
						tn.id
					)
					if (res.code !== 1) {
						return this.$message.error('删除用户失败！')
					}
					this.getUserList()
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