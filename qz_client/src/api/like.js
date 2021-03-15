import http from './http'
const like = {
  add(params) {
    return http.post('/api/like/add', params)
  },
  delete(params) {
    return http.post('/api/like/delete', params)
  },
  checkLike(params){
    return http.post('/api/like/checkLike', params)
  }
}

export default like