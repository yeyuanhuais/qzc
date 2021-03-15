import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

Vue.config.productionTip = false
/**
 * 获取当前时间
 * 格式YYYY-MM-DD
 */
Vue.prototype.getNowFormatDate = function () {
  var date = new Date()
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var strDate = date.getDate()
  var weeks = date.getDay()
  var week
  switch (weeks) {
  case 0:
    week = '星期日'
    break
  case 1:
    week = '星期一'
    break
  case 2:
    week = '星期二'
    break
  case 3:
    week = '星期三'
    break
  case 4:
    week = '星期四'
    break
  case 5:
    week = '星期五'
    break
  case 6:
    week = '星期六'
    break
  }
  var currentdate = `${year}年${month}月${strDate}日 ${week}`
  return currentdate
}
Vue.prototype.loading=0
// swiper  npm install swiper vue-awesome-swiper --save
import VueAwesomeSwiper from 'vue-awesome-swiper'
// require styles
import 'swiper/css/swiper.css'
Vue.use(VueAwesomeSwiper)

// 图标
import './assets/fonts/iconfont.css'

// 视频播放 npm install vue-video-player --save
import VueVideoPlayer from 'vue-video-player'
import 'video.js/dist/video-js.css'
import './plugins/element.js'
Vue.use(VueVideoPlayer)

import api from './api/index'
Vue.prototype.$api = api

Vue.prototype.$bus = new Vue()

// 时间格式化 npm install moment --save
import moment from 'moment'
import 'moment/locale/zh-cn'
moment.locale('zh-cn')
Vue.filter('dateString', function (value, format) {
  return moment(value).format(format)
})

// 富文本 npm install vue-quill-editor --save
import VueQuillEditor, { Quill } from 'vue-quill-editor'
// import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
// import 'quill/dist/quill.bubble.css'
Vue.use(VueQuillEditor)
import hljs from 'highlight.js'
import { ImageDrop } from 'quill-image-drop-module'
import ImageResize from 'quill-image-resize-module'
Quill.register('modules/imageDrop', ImageDrop)
Quill.register('modules/imageResize', ImageResize)
// 对html文字进行过滤
Vue.prototype.msg= function (msg) {
  return msg.replace(/<\/?.+?\/?>/g, '')
}
// npm install --save normalize.css
import 'normalize.css/normalize.css'
import './utils/flexible'
import loading from '@/components/loading'
Vue.component('loading',loading)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')