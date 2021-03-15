import {
  RECEIVE_NAVIGATION,
  RECEIVE_FOLKCUSTOM,
  GET_ARTICLE,
  RECEIVE_ARCHITECTURE,
  RECEIVE_LOCALISM,
  RECEIVE_RELIGION,
  RECEIVE_TRAVELNOTE,
  SET_LOGINWAY,
  RECEIVE_USER,
  LOGOUT,
  USERCODE,
  RECEIVE_REGION,
  RECEIVE_ARTICLECOUNT,
  RECEIVE_DRAMA,
  RECEIVE_DRAMACLASSNAME,
  GET_DRAMA
} from './mutations-type'
export default {
  [RECEIVE_NAVIGATION](state, { navigations }) {
    state.navigations = navigations
  },
  [RECEIVE_FOLKCUSTOM](state, { folkCustoms }) {
    state.folkCustoms = folkCustoms
  },
  [GET_ARTICLE](state, { article }) {
    state.article = article
  },
  [RECEIVE_ARCHITECTURE](state, { architectures }) {
    state.architectures = architectures
  },
  [RECEIVE_LOCALISM](state, { localisms }) {
    state.localisms = localisms
  },
  [RECEIVE_RELIGION](state, { religions }) {
    state.religions = religions
  },
  [RECEIVE_TRAVELNOTE](state, { travelNotes }) {
    state.travelNotes = travelNotes
  },
  [SET_LOGINWAY](state,loginWay){
    state.loginWay=loginWay
  },
  [RECEIVE_USER](state, user) {
    state.user = user
  },
  [LOGOUT](state) {
    state.user = {}
  },
  [USERCODE](state, userCode) {
    state.userCode = userCode
  },
  [RECEIVE_REGION](state, { regions }) {
    state.regions = regions
  },
  [RECEIVE_ARTICLECOUNT](state, articleCount) {
    state.articleCount = articleCount
  },
  [RECEIVE_DRAMA](state, { dramas }) {
    state.dramas = dramas
  },
  [RECEIVE_DRAMACLASSNAME](state, dramaClassName) {
    state.dramaClassName = dramaClassName
  },
  [GET_DRAMA](state, {drama}) {
    state.drama = drama
  }
}