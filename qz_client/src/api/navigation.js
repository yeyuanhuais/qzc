import http from'./http'
const navigation={
  navigationList(params){
    return http.post('/api/navigation/getAll',params)
  },
  getById(id){
    return http.get(`/api/navigation/getById?id=${id}`)
  },
  add(params){
    return http.post('/api/navigation/add', params)
  },
  edit(params){
    return http.put('/api/navigation/edit', params)
  },
  delete(id){
    return http.delete(`/api/navigation/delete?id=${id}`)
  },
  navigationStateChanged(id,isShow){
    return http.put(`/api/navigation/${id}/navigationStateChanged/${isShow}`)
  }
}

export default navigation