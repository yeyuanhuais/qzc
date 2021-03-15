import http from'./http'
const region={
  add(params){
    return http.post('/api/region/add', params)
  },
  getAll(params){
    return http.post('/api/region/getAll',params)
  },
  getById(id){
    return http.get(`/api/region/getById?id=${id}`)
  },
  edit(params){
    return http.put('/api/region/edit', params)
  },
  delete(id){
    return http.delete(`/api/region/delete?id=${id}`)
  }
}

export default region