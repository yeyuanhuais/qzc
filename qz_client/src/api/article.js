import http from'./http'
const article={
  articleList(id,num,size){
    return http.get(`/api/article/getByClassId?classId=${id}&num=${num}&size=${size}`)
  },
  articleOne(id){
    return http.get(`/api/article/getById?id=${id}`)
  },
  getAll(params) {
    return http.post('/api/article/getAll', params)
  },
  add(params){
    return http.post('/api/article/add', params)
  },
  edit(params){
    return http.put('/api/article/edit', params)
  },
  delete(id){
    return http.delete(`/api/article/delete?id=${id}`)
  },
  articleStateChanged(id,isShow){
    return http.put(`/api/article/${id}/articleStateChanged/${isShow}`)
  }
}
export default article