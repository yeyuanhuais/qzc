<!--  -->
<template>
	<div class="situation">
		<div class="mainHead">
			<h3>{{article.className}}</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item v-if="article.classId===1" :to="{ path: '/qzFolkCustom' }" replace>{{article.className}}</el-breadcrumb-item>
				<el-breadcrumb-item v-if="article.classId===2" :to="{ path: '/qzArchitecture' }">{{article.className}}</el-breadcrumb-item>
				<el-breadcrumb-item v-if="article.classId===3" :to="{ path: '/qzLocalism' }">{{article.className}}</el-breadcrumb-item>
				<el-breadcrumb-item v-if="article.classId===4" :to="{ path: '/qzReligion' }">{{article.className}}</el-breadcrumb-item>
				<el-breadcrumb-item>详细页</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<div class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
				<h2 class="title">{{article.title}}</h2>
				<div class="property">
					<span>{{article.source}}</span>
					<span>{{article.time}}</span>
				</div>
				<div class="contex" v-html="article.content"></div>
				<div class="usercan" v-show="Object.keys(user).length!==0">
					<Like :count="article.likeNum" :like="isLike" @click.native="like" />
					<Favorite :count="article.favoriteNum" :favorite="isFavorite" @click.native="favorite" />
				</div>
				<Comment v-show="Object.keys(user).length!==0" :article="article" :user="user"/>
			</div>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex'
import Like from './Like'
import Favorite from './Favorite'
import Comment from './Comment'
export default {
	data() {
		return {
			count: 0,
			likeList: {},
			isLike:false,
			favoriteList: {},
			isFavorite:false
		}
	},

	components: {
		Like,
		Favorite,
		Comment
	},

	computed: {
		...mapState(['article', 'user'])
	},

	// mounted() {
	// 	this.$store.dispatch('getArticle', this.$route.params.id)
	// },
	activated(){
		this.$store.dispatch('getArticle', this.$route.params.id)
	},
	created() {},
	methods: {
		load() {
			this.count += 3
		},
		like() {
			this.isLike = !this.isLike
			this.likeList.likeStatus=this.isLike
			this.changeLike()
		},
    async changeLike(){
			const {data:res}=await this.$api.like.add(this.likeList)
			if(res.code===1){
				this.$message.success('操作成功')
				this.$store.dispatch('getArticle', this.$route.params.id)
			}else{
				this.$message.error(res.message)
			}
    },
    async checkLike(){
      const {data:res}=await this.$api.like.checkLike(this.likeList)
      if(res.code===1){
				this.isLike=res.response.like
      }
    },
		favorite() {
			this.isFavorite = !this.isFavorite
			this.changeFavorite()
		},
    async changeFavorite(){
      const {data:res}=await this.$api.favorite.add(this.favoriteList)
			if(res.code===1){
				this.$message.success('操作成功')
				this.$store.dispatch('getArticle', this.$route.params.id)
			}else{
				this.$message.error(res.message)
			}
    },
    async checkFavorite(){
      const {data:res}=await this.$api.favorite.checkFavorite(this.favoriteList)
      if(res.code===1){
				this.isFavorite=res.response.favorite
				console.log(this.isFavorite)
      }
    }
	},
	watch: {
		article(value) {
			this.$nextTick(() => {
				this.likeList.objId = this.article.id
				this.likeList.objType = this.article.className
				this.likeList.userId = this.user.id
				this.favoriteList.objId = this.article.id
				this.favoriteList.objType = this.article.className
        this.favoriteList.userId = this.user.id
        if(Object.keys(this.user).length!==0){
					this.checkFavorite()
					this.checkLike()
				}
			})
		}
	}
}
</script>
<style lang="stylus" scoped>
// @import url("../assets/css/reset.css");
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
				>>>.icontent
					padding 0 2.5rem
					p
						padding 0 1.25rem
						margin 0
						line-height 2
						text-indent 2em
						font-family SimSun
						font-size 1.125rem
						img
							margin-top .625rem
			.usercan
				display flex
				justify-content flex-end
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.situation
		padding 0 2.125rem
		.mainHead
			display flex
			justify-content space-between
			.el-breadcrumb
				font-size .9375rem
				line-height 3.75rem
</style>