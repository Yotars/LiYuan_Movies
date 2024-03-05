<template>
  <div class="container">
    <!-- 用户交互区 -->
    <el-form ref="formRef" :model="query" label-width="auto">
      <el-form-item label="" prop="name">
        <el-input
          v-model="query.mName"
          placeholder="请输入电影名"
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
      width="600"
      height="750"
    >
      <el-table-column type="index" :index="(index: number) => index + 1" />

      <el-table-column type="expand" label="更多详情" width="85">
        <template #default="props">
          <div>
            {{ props.row }}
          </div>
        </template>
      </el-table-column>

      <!-- <el-table-column prop="mId" label="电影ID" /> -->

      <el-table-column prop="mName" label="电影名" width="250" />

      <el-table-column prop="mImage" label="电影封面">
        <template #default="scope">
          <img
            style="width: 33px"
            :src="`http://localhost:5173/src/assets/img/${scope.row.mImage}`"
            alt=""
            srcset=""
          />
        </template>
      </el-table-column>

      <!-- <el-table-column prop="director" label="导演" /> -->

      <!-- <el-table-column prop="actors" label="演员" /> -->

      <el-table-column prop="type" label="类型" width="250">
        <template #default="scope">
          <el-tag
            :style="{ marginRight: '6px' }"
            v-for="(i, index) in scope.row.type.split(' / ')"
            :key="index"
          >
            {{ i }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- <el-table-column prop="area" label="地区" /> -->

      <el-table-column prop="language" label="语言" width="250">
        <template #default="scope">
          <el-tag
            :style="{ marginRight: '6px' }"
            v-for="(i, index) in scope.row.language.split(' / ')"
            :key="index"
          >
            {{ i }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="launchDate" label="上映时间" />

      <!-- <el-table-column prop="filmLength" label="时长" /> -->

      <!-- <el-table-column prop="IMDb" label="IMDb" /> -->

      <!-- <el-table-column prop="Intro" label="简介" /> -->

      <el-table-column fixed="right" label="数据操作">
        <template #default="scope">
          <el-button type="primary">预览页面</el-button>
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
      width="700"
      align-center
    >
      <el-form
        ref="formRef"
        v-if="!isInfo"
        :model="modelData"
        label-width="auto"
      >
        <el-form-item label="电影名称">
          <el-input v-model="modelData.mName" />
        </el-form-item>
        <el-form-item label="电影类型">
          <el-input v-model="modelData.type" />
        </el-form-item>
        <el-form-item label="电影图片">
          <el-input v-model="modelData.mImage" />
        </el-form-item>
        <el-form-item label="导演">
          <el-input v-model="modelData.director" />
        </el-form-item>

        <el-form-item label="演员">
          <el-input v-model="modelData.actors" />
        </el-form-item>

        <el-form-item label="语言">
          <el-input v-model="modelData.language" />
        </el-form-item>
        <el-form-item label="地区">
          <el-input v-model="modelData.area" />
        </el-form-item>

        <el-form-item label="片长">
          <el-input v-model="modelData.filmLength" />
        </el-form-item>

        <el-form-item label="上映时间">
          <el-input v-model="modelData.launchDate" />
        </el-form-item>

        <el-form-item label="电影简介">
          <el-input
            v-model="modelData.Intro"
            :rows="5"
            type="textarea"
            placeholder="请输入"
          />
        </el-form-item>

        <el-form-item label="IMDb">
          <el-input v-model="modelData.IMDb" />
        </el-form-item>
      </el-form>

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
import { movies } from "@/api/request";
import { ElMessage } from "element-plus";

const list = reactive([
  {
    mId: 0,
    mName: "",
    mImage: "",
    director: 1,
    actors: "",
    type: "",
    area: "",
    language: "",
    launchDate: "",
    filmLength: "",
    IMDb: "",
    Intro: "",
  },
]);

const query = ref({
  current: 1,
  size: 10,
  pages: 0,
  total: 0,
  mName: "",
  launchDate: "",
  type: "",
});

const model = ref(false);
const modelTitle = ref("模态框");
const method = ref("");
const isInfo = ref(false);

interface movie {
  mId: number;
  mName: string;
  mImage: string;
  director: string;
  actors: string;
  type: string;
  area: string;
  language: string;
  launchDate: string;
  filmLength: string;
  IMDb: string;
  Intro: string;
}
let modelNull = {
  mId: 0,
  mName: "",
  mImage: "",
  director: "",
  actors: "",
  type: "",
  area: "",
  language: "",
  launchDate: "",
  filmLength: "",
  IMDb: "",
  Intro: "",
};

let modelData: movie = modelNull;

const getList = async () => {
  const { result, message }: any = await movies.list(query.value);
  list.splice(0);
  list.push(...result.records);
  query.value = Object.assign({}, query.value, result.pagination);
  ElMessage({
    message: message,
    type: "success",
  });
};
const insert = async (item: object) => {
  const { result, message }: any = await movies.insert(item);
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
  const { result, message }: any = await movies.update(item);
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
  const { result, message }: any = await movies.delete(item);
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
const button = (operation: string, row: movie = modelNull) => {
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
