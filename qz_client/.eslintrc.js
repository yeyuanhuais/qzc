module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  parserOptions: {
    parser: 'babel-eslint'
  },
  rules: {
    'no-console': 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'space-before-function-paren':'off',
    'space-before-blocks':'off',
    'keyword-spacing':'off',
    'key-spacing':'off',
    'eol-last':'off',
    'import/first':'off',
    'indent':'off',
    'no-tabs':'off',
    'prefer-const':'off',
    'space-infix-ops':'off',
    'object-curly-spacing':'off',
    'comma-spacing':'off',
    'no-unused-vars':'off',
    'no-undef':'off'
  }
}
