<!--  -->
<template>
	<div class="situation">
		<div class="mainHead">
			<h3>游记感悟</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>游记感悟</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
				<div
					class="tnone"
					v-for="item in travelNotes"
					:key="item.id"
					:v-show="item.isSow"
					@click="open(item.id)"
				>
					<div v-bind:style="{backgroundImage:'url('+item.imagePath+')'}" class="img">
						<div class="user"></div>
						<div class="by">{{item.source}}</div>
					</div>
					<div class="text">
						<h4>{{item.title}}</h4>
						<p
							v-html="msg(item.content).length > 100 ? msg(item.content).slice(0, 100) + '...' :msg(item.content)"
						></p>
					</div>
				</div>
			</ul>
		</div>
		<el-dialog title="游记详情" :visible.sync="dialogVisible" custom-class="eldialog">
			<img :src="travelNote.imagePath" alt="">
			<p v-html="travelNote.content"></p>
		</el-dialog>
	</div>
</template>

<script>
import { mapState } from 'vuex'
export default {
	data() {
		return {
			count: this.count1,
			params: {
				keyword: '',
				num: 1,
				size: 100
			},
			dialogVisible: false,
			travelNote:{}
		}
	},
	mounted() {
		this.$store.dispatch('getTravelNoteList', this.params)
	},
	methods: {
		// 滚动数
		load() {
			this.count += 1
		},
		async open(id){
			this.dialogVisible=true
			const{data:res}=await this.$api.travelNotes.getById(id)
			if(res.code!==1){
				this.dialogVisible=false
				return this.$message.error('获取详情失败')
			}
			this.travelNote=res.response
		}
	},
	computed: {
		...mapState(['travelNotes']),
		count1() {
			return this.travelNotes.length
		}
	}
}
</script>
<style lang='stylus'>
.situation
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
		margin-top 1.25rem
		.infinite-list
			display flex
			flex-flow row wrap
			width 100%
			height 38.0625rem
			.tnone
				box-shadow 0 .125rem .75rem 0 rgba(0, 0, 0, 0.1)
				border-radius .25rem
				background-color #ffffff
				width 24.375rem
				margin .625rem .625rem 0 0
				.img
					width 24.375rem
					height 14.375rem
					background-size cover
					background-repeat no-repeat
					.user
						margin-left .3125rem
						width 2.8125rem
						height 2.8125rem
						border-radius 50%
						position relative
						top 11.5625rem
						background-image url('../assets/images/admin.jpg')
						background-size cover
					.by
						display flex
						align-items center
						font-size .8125rem
						background-color rgba(225, 225, 225, 0.3)
						position relative
						top 8.75rem
						height 2.8125rem
						padding-left 3.75rem
				.text
					margin 1.25rem
					h4
						line-height 1.5625rem
						font-size 1.25rem
					p
						margin .3125rem 0
						line-height 1.25rem
	.el-dialog
		// width 40%
		p
			margin .625rem
			line-height 1.875rem
	.eldialog
		width	40%
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.situation
		padding 0 2.125rem
		.mainHead
			display flex
			justify-content space-between
			.el-breadcrumb
				font-size .9375rem
				line-height 3.75rem
		.mainBody
			.infinite-list
				padding 0
				width 100%
				height 99%
				.tnone
					width 19.375rem
					.img
						width 19.375rem
						height 9.375rem
						.user
							top 6.55rem
						.by
							top 3.75rem
		.el-dialog
			img
				width 100%
			p
				margin .625rem
				line-height 1.875rem
		.eldialog
			width	80%
</style>
