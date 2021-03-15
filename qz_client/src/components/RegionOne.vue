<!--  -->
<template>
	<div class="situation">
		<div class="mainHead">
			<h3>景点介绍</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item :to="{ path: '/qzRegion' }">景点</el-breadcrumb-item>
				<el-breadcrumb-item>详细页</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<div class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
				<h2 class="title">{{region.title}}</h2>
				<div class="property">
					<span>{{region.source}}</span>
					<span>{{region.creatTime}}</span>
				</div>
				<div class="contex" v-html="region.content"></div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
      region:{},
      count:0
		}
	},
	// mounted() {
  //   this.getRegion()
	// },
	activated(){
		this.getRegion()
	},
	created() {},
	methods: {
		load() {
			this.count += 3
		},
    async getRegion(){
			const {data:res}=await this.$api.region.getById(this.$route.params.id)
			if(res.code===1){
        this.region=res.response
        console.log(res)
			}else{
				return this.$message.error(res.message)
			}
    }
	}
}
</script>
<style lang="stylus" scoped>
// @import "../assets/css/reset.css";
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
			width 12.5rem
			font-size 0.9375rem
			line-height 3.75rem
	.mainBody
		.infinite-list
			margin 1.875rem .625rem 0 .625rem
			background-color rgba(255, 255, 255, 0.95)
			height 99%
			.title
				font-size 1.625rem
				line-height 1.5
				text-align center
				margin 1.25rem 0
				color #ba0f0f
				font-weight bold
			.property
				font-size .875rem
				margin-bottom 1.25rem
				padding .375rem .625rem
				text-align center
				color #333
				border-bottom 0.0625rem #ccc dotted
				span
					padding 0 .625rem
			.contex
				color #333
				margin 0 1.875rem
				>>>ul
					li
						strong
							margin .3125rem 0 .3125rem 1.25rem
							line-height 1.875rem
				>>>p
					line-height 1.875rem
					text-indent 1.875rem
</style>