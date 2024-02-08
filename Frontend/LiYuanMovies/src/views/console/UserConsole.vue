<template>
  <div class="container">
    <!-- 用户交互区 -->
    <el-form ref="formRef" :model="query" label-width="auto">
      <el-form-item label="" prop="name">
        <el-input
          v-model="query.username"
          placeholder="请输入用户名"
          :prefix-icon="Search"
          style="width: 490px"
          size="large"
          clearable
        />
        <el-button
          style="margin-left: 20px; margin-right: 5px"
          @click="getList"
          type="primary"
          plain
          >查找</el-button
        >
        <el-button @click="button('insert')" type="success">添加</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据展示区 -->
    <el-table
      border
      table-layout="auto"
      :data="list"
      stripe
      style="width: 100%"
    >
      <el-table-column type="index" :index="(index: number) => index + 1" />

      <el-table-column prop="uId" label="用户ID" width="150" />

      <el-table-column prop="username" label="用户名" width="150" />

      <el-table-column prop="password" label="用户密码" width="150" />

      <el-table-column prop="power" label="用户权限" width="150">
        <template #default="scope">
          <el-tag v-if="scope.row.power === 0" type="primary">管理</el-tag>
          <el-tag v-else type="success">用户</el-tag>
        </template>
      </el-table-column>

      <el-table-column fixed="right" width="220" label="用户操作">
        <template #default="scope">
          <el-button type="primary" @click="button('info', scope.row)">
            用户信息
          </el-button>
          <el-button type="success" @click="button('infoUpdate', scope.row)">
            编辑信息
          </el-button>
        </template>
      </el-table-column>

      <el-table-column fixed="right" width="220" label="数据操作">
        <template #default="scope">
          <el-button type="warning" @click="button('update', scope.row)">
            编辑数据
          </el-button>
          <el-button type="danger" @click="button('delete', scope.row)">
            删除数据
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页区 -->
    <el-pagination
      v-model:current-page="query.current"
      v-model:page-size="query.size"
      :total="query.total"
      :page-sizes="[10, 20]"
      layout="total, prev, pager, next, sizes"
      @size-change="getList()"
      @current-change="getList()"
    />

    <!-- 模态框 -->
    <el-dialog
      v-model="model"
      :close="close()"
      destroy-on-close
      :title="modelTitle"
      width="500"
      align-center
    >
      <span v-if="modelTitle === '删除数据'">
        <el-text type="danger"> <h1>此操作不可逆，确认删除?</h1> </el-text>
      </span>
      <span v-else>
        <el-form
          ref="formRef"
          v-if="!isInfo"
          :model="modelData"
          label-width="auto"
        >
          <el-form-item label="用户ID" prop="uId">
            <el-input v-model="modelData.uId" disabled />
          </el-form-item>

          <el-form-item label="用户名" prop="username">
            <el-input v-model="modelData.username" placeholder="请输入用户名" />
          </el-form-item>

          <el-form-item label="用户密码" prop="password">
            <el-input
              v-model="modelData.password"
              placeholder="请输入用户密码"
            />
          </el-form-item>

          <el-form-item label="用户权限" prop="power">
            <el-radio-group v-model="modelData.power">
              <el-radio-button :label="1" size="large" border>
                用户
              </el-radio-button>
              <el-radio-button
                :label="0"
                :disabled="modelTitle === '添加数据'"
                size="large"
                border
              >
                管理
              </el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-form>

        <el-form v-else ref="formRef" :model="f.data" label-width="auto">
          <el-form-item label="用户ID" prop="uId">
            <el-input v-model="f.data.uId" disabled />
          </el-form-item>

          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="f.data.nickname" />
          </el-form-item>

          <el-form-item label="头像" prop="head">
            <el-input v-model="f.data.head" />
          </el-form-item>

          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="f.data.sex">
              <el-radio-button label="1" size="large" border
                >男</el-radio-button
              >
              <el-radio-button label="0" size="large" border
                >女</el-radio-button
              >
            </el-radio-group>
          </el-form-item>

          <el-form-item label="生日" prop="birth">
            <el-date-picker
              v-model="f.data.birth"
              type="date"
              format="YYYY年 MM月 DD日"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>

          <el-form-item label="地址" prop="address">
            <el-input v-model="f.data.address" />
          </el-form-item>

          <el-form-item label="电话" prop="phone">
            <el-input v-model="f.data.phone" />
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="f.data.email" />
          </el-form-item>
        </el-form>
      </span>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取消</el-button>
          <el-button type="primary" @click="done">确认</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import { user, info } from "@/api/request";
import { ElMessage } from "element-plus";

const list = reactive([
  {
    uId: 0,
    username: "test",
    password: "123456",
    power: 1,
  },
]);

const f = reactive({
  data: {
    uId: 0,
    nickname: "",
    head: "",
    sex: 0,
    birth: "",
    address: "",
    phone: "",
    email: "",
  },
});

const query = ref({
  current: 1,
  size: 10,
  pages: 0,
  total: 0,
  username: "",
});

const getList = async () => {
  const { result, message }: any = await user.list(query.value);
  list.splice(0, list.length);
  list.push(...result.records);
  query.value = Object.assign({}, query.value, result.pagination);
  ElMessage({
    message: message,
    type: "success",
  });
};
const insert = async (item: object) => {
  const { result, message }: any = await user.insert(item);
  if (result.is) {
    ElMessage({
      message: message,
      type: "success",
    });
  } else {
    ElMessage.error(message);
  }
  getList();
};
const update = async (item: object) => {
  const { result, message }: any = await user.update(item);
  if (result.is) {
    ElMessage({
      message: message,
      type: "success",
    });
  } else {
    ElMessage.error(message);
  }
  getList();
};
const del = async (item: object) => {
  const { result, message }: any = await user.delete(item);
  if (result.is) {
    ElMessage({
      message: message,
      type: "success",
    });
  } else {
    ElMessage.error(message);
  }
  getList();
};
const getInfo = async (item: object) => {
  const { result, message }: any = await info.list(item);
  f.data = Object.assign({}, f.data, result.records);
  ElMessage({
    message: message,
    type: "success",
  });
};
const updateInfo = async (item: object) => {
  const { result, message }: any = await info.update(item);
  if (result.is) {
    ElMessage({
      message: message,
      type: "success",
    });
  } else {
    ElMessage.error(message);
  }
  getList();
};

const model = ref(false);
const modelTitle = ref("模态框");
const method = ref("");
const isInfo = ref(false);
let modelData:
  | {
      [key: string]: string;
    }
  | {
      uId: string;
      username: string;
      password: string;
      power: number;
    };

let modelNull = {
  uId: "",
  username: "",
  password: "",
  power: 1,
};

const button = (operation: string, row: object = {}) => {
  const operations: { [key: string]: Function } = {
    insert: () => {
      modelData = reactive({ ...modelNull });
      modelTitle.value = "添加数据";
    },
    update: () => {
      modelData = reactive({ ...row });
      modelTitle.value = "编辑数据";
    },
    delete: () => {
      modelData = reactive({ ...row });
      modelTitle.value = "删除数据";
    },
    info: () => {
      getInfo(row);
      modelTitle.value = "用户信息";
      isInfo.value = true;
    },
    infoUpdate: () => {
      getInfo(row);
      modelTitle.value = "编辑用户信息";
      isInfo.value = true;
    },
  };

  operations[operation]();
  method.value = operation;
  model.value = true;
};

const done = () => {
  const operations: { [key: string]: Function } = {
    insert: () => {
      insert(modelData);
    },
    update: () => {
      update(modelData);
      console.log(method.value);
    },
    delete: () => {
      del(modelData);
      console.log(method.value);
    },
    info: () => {
      console.log(method.value);
    },
    infoUpdate: () => {
      updateInfo(f.data);
      console.log(method.value);
    },
  };

  operations[method.value]();
  isInfo.value = false;
  model.value = false;
};

const cancel = () => {
  isInfo.value = false;
  model.value = false;
};

let i = 0;
const close = () => {
  i++;
  if (!model.value && i > 2) {
    isInfo.value = false;
    model.value = false;
    i = 2;
  }
};

getList();
</script>

<style scoped></style>
