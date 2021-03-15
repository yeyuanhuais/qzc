import http from './http'
const login = {
  login(params) {
    return http.post('/api/user/login', params)
  },
  register(params) {
    return http.post('/api/user/register', params)
  },
  sendMimeMail(userName,email) {
    return http.get(`/api/user/sendMimeMail?userName=${userName}&email=${email}`)
  },
  getCaptcha() {
    return http.get('/api/user/verification')
  }
}

export default login