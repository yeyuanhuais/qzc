import Vue from 'vue'
import {
  RECEIVE_NAVIGATION,
  RECEIVE_FOLKCUSTOM,
  GET_ARTICLE,
  RECEIVE_ARCHITECTURE,
  RECEIVE_LOCALISM,
  RECEIVE_RELIGION,
  RECEIVE_TRAVELNOTE,
  RECEIVE_USER,
  LOGOUT,
  RECEIVE_REGION,
  RECEIVE_ARTICLECOUNT,
  RECEIVE_DRAMA,
  RECEIVE_DRAMACLASSNAME,
  GET_DRAMA
} from './mutations-type'
import api from '../api/index'
export default {
  async getNavigationList({ commit }, params) {
    ++Vue.prototype.loading
    console.log('%c [  Vue.prototype.loading 1]', 'font-size:15px; background:pink; color:#bf2c9f;',  Vue.prototype.loading)
    const result = await api.navigation.navigationList(params)
    if (result.data.code === 1) {
       --Vue.prototype.loading
      console.log('%c [  Vue.prototype.loading1 ]', 'font-size:15px; background:pink; color:#bf2c9f;',  Vue.prototype.loading)
      const navigations = result.data.response
      commit(RECEIVE_NAVIGATION, { navigations })
    }
  },
  async getDramaList({ commit }, params) {
    const result = await api.drama.getDrama(params)
    if (result.data.code === 1) {
      const dramas = result.data.response
      commit(RECEIVE_DRAMA, { dramas })
    }
  },
  async getDramaClassName({ commit }) {
    const result = await api.drama.getClassName()
    if (result.data.code === 1) {
      const className = result.data.response
      commit(RECEIVE_DRAMACLASSNAME, className)
    }
  },
  // 根据id获取戏剧
  async getDrama({ commit }, id) {
    const result = await api.drama.getById(id)
    if (result.data.code === 1) {
      const drama = result.data.response
      commit(GET_DRAMA, { drama })
    }
  },
  async getFolkCustomList({ commit }, params) {
    const result = await api.article.articleList(1, params.num, params.size)
    if (result.data.code === 1) {
      const folkCustoms = result.data.response
      const count = result.data.count
      commit(RECEIVE_FOLKCUSTOM, { folkCustoms })
      commit(RECEIVE_ARTICLECOUNT, count)
    }
  },
  // 根据id获取文章
  async getArticle({ commit }, id) {
    const result = await api.article.articleOne(id)
    if (result.data.code === 1) {
      const article = result.data.response
      commit(GET_ARTICLE, { article })
    }
  },
  async getArchitectureList({ commit }, params) {
    const result = await api.article.articleList(2, params.num, params.size)
    if (result.data.code === 1) {
      const architectures = result.data.response
      const count = result.data.count
      commit(RECEIVE_ARTICLECOUNT, count)
      commit(RECEIVE_ARCHITECTURE, { architectures })
    }
  },
  async getLocalismList({ commit }, params) {
    const result = await api.article.articleList(3, params.num, params.size)
    if (result.data.code === 1) {
      const localisms = result.data.response
      const count = result.data.count
      commit(RECEIVE_ARTICLECOUNT, count)
      commit(RECEIVE_LOCALISM, { localisms })
    }
  },
  async getReligionList({ commit }, params) {
     ++Vue.prototype.loading
     console.log('%c [ prototype.loading2 ]', 'font-size:15px; background:pink; color:#bf2c9f;', Vue.prototype.loading)
    const result = await api.article.articleList(4, params.num, params.size)
    if (result.data.code === 1) {
       --Vue.prototype.loading
       console.log('%c [ Vue.prototype 2]', 'font-size:15px; background:pink; color:#bf2c9f;', Vue.prototype.loading)
      const religions = result.data.response
      const count = result.data.count
      commit(RECEIVE_ARTICLECOUNT, count)
      commit(RECEIVE_RELIGION, { religions })
    }
  },
  async getTravelNoteList({ commit }, params) {
    const result = await api.travelNotes.travelNotesList(params)
    if (result.data.code === 1) {
      const travelNotes = result.data.response
      commit(RECEIVE_TRAVELNOTE, { travelNotes })
    }
  },
  logout({ commit }) {
    localStorage.removeItem('user')
    localStorage.removeItem('token')
    localStorage.setItem('isRole', 0)
    commit(LOGOUT)
  },
  // 同步记录用户信息
  recordUser({ commit }, userInfo) {
    commit(RECEIVE_USER, userInfo)
  },
  async getRegionList({ commit }, params) {
     ++Vue.prototype.loading
     console.log('%c [ Vue.prototype 3]', 'font-size:15px; background:pink; color:#bf2c9f;', Vue.prototype.loading)
    const result = await api.region.getAll(params)
    if (result.data.code === 1) {
       --Vue.prototype.loading
       console.log('%c [ Vue.prototype3 ]', 'font-size:15px; background:pink; color:#bf2c9f;', Vue.prototype.loading)
      const regions = result.data.response
      commit(RECEIVE_REGION, { regions })
    }
  }
}