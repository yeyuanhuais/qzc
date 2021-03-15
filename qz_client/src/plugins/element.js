import Vue from 'vue'
import {
  Button,
  Breadcrumb,
  BreadcrumbItem,
  InfiniteScroll,
  Message,
  Card,
  Tabs,
  TabPane,
  Form,
  FormItem,
  Input,
  Upload,
  Switch,
  Select,
  Option,
  Container,
  Header,
  Aside,
  Menu,
  MenuItem,
  Main,
  Row,
  Col,
  Table,
  TableColumn,
  Dialog,
  Pagination,
  Tooltip,
  MessageBox
} from 'element-ui'

Vue.use(Button)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(InfiniteScroll)
Vue.use(Card)
Vue.use(Tabs)
Vue.use(TabPane)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Upload)
Vue.use(Switch)
Vue.use(Select)
Vue.use(Option)
Vue.use(Container)
Vue.use(Aside)
Vue.use(Menu)
Vue.use(Header)
Vue.use(MenuItem)
Vue.use(Main)
Vue.use(Row)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Dialog)
Vue.use(Col)
Vue.use(Pagination)
Vue.use(Tooltip)
Vue.prototype.$message = Message
Vue.prototype.$confirm = MessageBox.confirm