import http from './http'
const user={
  getAll(params) {
    return http.post('/api/user/getAll', params)
  },
  add(params){
    return http.post('/api/user/add', params)
  },
  edit(params){
    return http.put('/api/user/edit', params)
  },
  delete(id){
    return http.delete(`/api/user/delete?id=${id}`)
  },
  getById(id){
    return http.post(`/api/user/getById/${id}`)
  },
  userStateChanged(id,isShow){
    return http.put(`/api/user/${id}/userStateChanged/${isShow}`)
  },
  userRoleChanged(id,isShow){
    return http.put(`/api/user/${id}/userRoleChanged/${isShow}`)
  },
  resetPassword(id){
    return http.get(`/api/user/resetPassword?id=${id}`)
  }
}
export default user