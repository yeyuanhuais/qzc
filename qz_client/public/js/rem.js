!function(n){
  var  e=n.document,
       t=e.documentElement,
       i=800,
       d=i/16,
       o="orientationchange"in n?"orientationchange":"resize",
       a=function(){
           var n=t.clientWidth||320;n>720&&(n=720);
           t.style.fontSize=n/d+"px"
       };
       e.addEventListener&&(n.addEventListener(o,a,!1),e.addEventListener("DOMContentLoaded",a,!1))
}(window);