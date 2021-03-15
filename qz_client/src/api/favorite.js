import http from './http'
const favorite = {
  add(params) {
    return http.post('/api/favorite/add', params)
  },
  checkFavorite(params){
    return http.post('/api/favorite/checkFavorite', params)
  }
}

export default favorite