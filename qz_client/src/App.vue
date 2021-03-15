<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import {RECEIVE_USER} from './store/mutations-type'
export default {
  name: 'app',
  mounted(){
    const user=JSON.parse(localStorage.getItem('user'))
    if(user!==null){
      this.$store.commit(RECEIVE_USER,user)
    }
  },
  components:{
  }
}
</script>

<style lang="stylus" scoped>
#app
  width 100%
  height 100%
</style>
