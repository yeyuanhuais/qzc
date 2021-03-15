import http from './http'
const upload = {
  uploadimage(params) {
    return http.post('/api/upload/uploadimage', params)
  },
  delete(imageKey) {
    return http.delete(`/api/upload/delete?imageKey=${imageKey}`)
  }
}

export default upload