import http from'./http'
const drama={
  getClassName(){
    return http.get('/api/drama/getClassName')
  },
  getDrama(className){
    return http.get(`/api/drama/getDrama?className=${className}`)
  },
  getAll(params) {
    return http.post('/api/drama/getAll', params)
  },
  add(params){
    return http.post('/api/drama/add', params)
  },
  edit(params){
    return http.put('/api/drama/edit', params)
  },
  delete(id){
    return http.delete(`/api/drama/delete?id=${id}`)
  },
  getById(id){
    return http.get(`/api/drama/getById?id=${id}`)
  }
}
export default drama