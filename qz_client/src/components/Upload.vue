<template>
	<el-upload
		ref="upload"
		action="/api/upload/uploadimage"
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
				<!-- <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
									<i class="el-icon-zoom-in"></i>
				</span>-->
				<span v-if="!disabled" class="el-upload-list__item-delete" @click="handleDownload(file)">
					<i class="el-icon-download"></i>
				</span>
				<span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
					<i class="el-icon-delete"></i>
				</span>
			</span>
		</div>
	</el-upload>
</template>

<script>
export default {
  data(){
    return{
      num: 1,
			fileList: [],
      disabled: false,
      imageKey:''
    }
  },
  methods:{
    // 图片上传
		async uploadUrlAdd(file) {
			let fd = new FormData()
			fd.append('file', file.file)
			const { data: res } = await this.$api.upload.uploadimage(fd, {
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			})
			if(res.code!==1){
				return this.$message.error('图片上传失败！')
      }
      this.imageKey=res.response.fileName
      this.$emit('getPic',res.response)
		},
		async handleRemove(file,value) {
			if (file.status === 'success') {
				const { data: res } = await this.$api.upload.delete(
					this.imageKey
				)
				if (res.code !== 1) {
					return this.$message.error('删除图片失败！')
				}
				this.fileList = []
				return this.$message.success('删除图片成功！')
			}
		},
		handleDownload(file) {
			// console.log(file)
		},
		// 上传成功后的回调
		uploadSuccess(response, file, fileList) {
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

<style>
</style>