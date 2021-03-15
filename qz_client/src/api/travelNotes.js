import http from'./http'
const travelNotes={
  travelNotesList(params){
    return http.post('/api/travelNotes/getAll',params)
  },
  add(params){
    return http.post('/api/travelNotes/add', params)
  },
  edit(params){
    return http.put('/api/travelNotes/edit', params)
  },
  delete(id){
    return http.delete(`/api/travelNotes/delete?id=${id}`)
  },
  travelNotesStateChanged(id,isShow){
    return http.put(`/api/travelNotes/${id}/travelNotesStateChanged/${isShow}`)
  },
  getById(id){
    return http.get(`/api/travelNotes/getById?id=${id}`)
  }
}

export default travelNotes