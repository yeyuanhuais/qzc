import http from './http'
const comment = {
  addRoot(params) {
    return http.post('/api/comments/addRootComments', params)
  },
  addSon(params) {
    return http.post('/api/comments/addSonComments', params)
  },
  getCommentsByOwnerId(id,type) {
    return http.get(`/api/comments/getCommentsByOwnerId?id=${id}&type=${type}`)
  },
  getCommentsByUserId(userid){
    return http.get(`/api/comments/getCommentsByUserId?userid=${userid}`)
  },
  delete(id){
    return http.delete(`/api/comments/delete?id=${id}`)
  },
  getAll(params){
    return http.post('/api/comments/getAll',params)
  }
}

export default comment