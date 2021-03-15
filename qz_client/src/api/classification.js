import http from './http'
const classification = {
  getAll(params) {
    return http.post('/api/classification/getAll', params)
  },
  add(params){
    return http.post('/api/classification/add', params)
  },
  edit(params){
    return http.put('/api/classification/edit', params)
  },
  delete(id){
    return http.delete(`/api/classification/delete?id=${id}`)
  },
  getById(id){
    return http.get(`/api/classification/getById?id=${id}`)
  }
}

export default classification