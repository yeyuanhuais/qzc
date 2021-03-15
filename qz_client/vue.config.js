const webpack = require('webpack')
const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}
//用于生产环境去除多余的css
const PurgecssPlugin = require("purgecss-webpack-plugin")
//压缩代码并去掉console
const UglifyJsPlugin = require("uglifyjs-webpack-plugin")

module.exports = {
  lintOnSave:false,
  publicPath: './',
  productionSourceMap: false, // 是否在构建生产包时生成 sourceMap 文件，false将提高构建速度
  devServer: {
    // host: '0.0.0.0',
    port: 8080,
    https: false,
    hotOnly: false,
    disableHostCheck: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8001', //目标地址121.36.70.68
        ws: true, //// 是否启用websockets
        changeOrigin: true, //开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样服务端和服务端进行数据的交互就不会有跨域问题
        pathRewrite: { '^/api': 'http://localhost:8080/api' } //这里重写路径
      }
    },
    before: app => {}
  },
  chainWebpack: config => {
    config.resolve.alias.set('@', resolve('src'))

    config.plugin('provide').use(
      webpack.ProvidePlugin, [{
        'window.Quill': 'quill/dist/quill.js',
        'Quill': 'quill/dist/quill.js'
      }])
    /* 添加分析工具 */
    if (process.env.NODE_ENV === 'production') {
      config
        .plugin('webpack-bundle-analyzer')
        .use(require('webpack-bundle-analyzer').BundleAnalyzerPlugin)
        .end()
      config.plugins.delete('prefetch')
    } else {}
  },
  configureWebpack: (config) => {
    const plugins = []
    //去掉不用的css 多余的css
    plugins.push(
      new PurgecssPlugin({
        extractors: [
        {
          extractor: class Extractor {
            static extract(content) {
              const validSection = content.replace(
                /<style([\s\S]*?)<\/style>+/gim,
                ""
              );
              return validSection.match(/[A-Za-z0-9-_:/]+/g) || [];
            }
          },
          extensions: ["html", "vue"]
        }],
        whitelist: ["html", "body"],
        whitelistPatterns: [/el-.*/],
        whitelistPatternsChildren: [/^token/, /^pre/, /^code/]
      })
    )
  }
}