import Vue from 'vue'
import VueRouter from 'vue-router'
import { Message } from 'element-ui'
const Index = () => import('../views/Index')
const QzSituation = () => import('../views/QzSituation')
const QzDrama = () => import('../views/QzDrama')
const QzFolkCustom = () => import('../views/QzFolkCustom')
const Article = () => import('../components/Article')
const QzArchitecture = () => import('../views/QzArchitecture')
const QzLocalism = () => import('../views/QzLocalism')
const QzReligion = () => import('../views/QzReligion')
const QzTravelNotes = () => import('../views/QzTravelNotes')
const Login = () => import('../views/Login')
const User = () => import('../views/User')
const ArticleAdmin = () => import('../views/admin/Article')
const Navigation = () => import('../views/admin/Navigation')
const TravelNotes = () => import('../views/admin/TravelNotes')
const Admin = () => import('../views/admin/Admin')
const All = () => import('../views/All')
const RegionOne = () => import('../components/RegionOne')
const QzRegion = () => import('../views/QzRegion')
const Region = () => import('../views/admin/Region')
const UserAdmin = () => import('../views/admin/User')
const Comments = () => import('../views/admin/Comments')
const DramaClass = () => import('../components/DramaClass.vue')
const Drama = () => import('../components/Drama.vue')
Vue.use(VueRouter)

const routes = [
{
  path: '/',
  name: 'All',
  component: All,
  redirect: '/index',
  children: [
  {
    path: '/index',
    name: 'Index',
    component: Index
  },{
    path: '/qzSituation',
    name: 'QzSituation',
    component: QzSituation
  }, {
    path: '/qzDrama',
    name: 'QzDrama',
    component: QzDrama,
    children:[{
      path: '/qzDrama',
      redirect: '/qzDrama/高甲戏'
    },{
      path: '/qzDrama/:className',
      name: 'DramaClass',
      component: DramaClass
    }]
  },{
    path: '/qzDrama/:className/:id',
    name: 'Drama',
    component: Drama
  }, {
    path: '/qzFolkCustom',
    name: 'QzFolkCustom',
    component: QzFolkCustom
  },{
    path: '/qzFolkCustom/:id',
    name: 'Article',
    component: Article
  }, {
    path: '/qzArchitecture',
    name: 'QzArchitecture',
    component: QzArchitecture
  },
  {
    path: '/qzArchitecture/:id',
    name: 'Article',
    component: Article
  }, {
    path: '/qzLocalism',
    name: 'QzLocalism',
    component: QzLocalism
  },
  {
    path: '/qzLocalism/:id',
    name: 'Article',
    component: Article
  }, {
    path: '/qzReligion',
    name: 'QzReligion',
    component: QzReligion
  },
  {
    path: '/qzReligion/:id',
    name: 'Article',
    component: Article
  }, {
    path: '/qzTravelNotes',
    name: 'QzTravelNotes',
    component: QzTravelNotes
  }, {
    path: '/qzRegion',
    name: 'QzRegion',
    component: QzRegion
  },
  {
    path: '/qzRegion/:id',
    name: 'RegionOne',
    component: RegionOne
  }, {
    path: '/login',
    name: 'Login',
    component: Login
  }, {
    path: '/user',
    name: 'User',
    component: User
  }]
}, {
  path: '/admin',
  name: 'Admin',
  component: Admin,
  meta: { // 在路由配置中加入meta:{requireAuth: true}
    requireAuth: true
  },
  children: [{
    path: '/admin/travelNotes',
    name: 'TravelNotes',
    component: TravelNotes
  }, {
    path: '/admin/navigation',
    name: 'Navigation',
    component: Navigation
  }, {
    path: '/admin/article',
    name: 'ArticleAdmin',
    component: ArticleAdmin
  }, {
    path: '/admin/comments',
    name: 'Comments',
    component: Comments
  }, {
    path: '/admin/user',
    name: 'UserAdmin',
    component: UserAdmin
  }, {
    path: '/admin/region',
    name: 'Region',
    component: Region
  }]
}]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to, from, next) => {
  if (to.matched.some(res => res.meta.requireAuth)) {
    let isRole = localStorage.getItem('isRole')
    console.log(isRole)
    isRole = Boolean(Number(isRole))
    if (isRole) {
      next()
    } else {
      Message.error('无权限')
      next({
        path: '/login', // 未登录则跳转至login页面
        query: { from: to.fullPath } // 登陆成功后回到当前页面，这里传值给login页面，to.fullPath为当前点击的页面
      })
    }
  } else {
    next()
  }
})

export default router