import store from '@/store'
// 设置基础字号
const baseSize = 16
// 设置基本缩放比
let scaleRate = 1
// 设置 rem 函数
function setRem() {
  // 当前页面宽度相对于 1920 宽的缩放比例，可根据自己需要修改。
  const scale = document.documentElement.clientWidth / 1440
  console.log('%c [ scale ]', 'font-size:15px; background:pink; color:#bf2c9f;', scale)
  scaleRate = scale
  // 设置页面根节点字体大小
  if (document.documentElement.clientWidth >= 1024) {
    document.documentElement.style.fontSize = (baseSize * scale)+ 'px'
  }else {
    document.documentElement.style.fontSize = (12 * scale) + 'px'
  }
}
// 初始化
setRem()
// 改变窗口大小时重新设置 rem
window.onresize = function () {
  setRem()
}
export default scaleRate