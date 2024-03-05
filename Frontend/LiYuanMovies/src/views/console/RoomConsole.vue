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
      <el-table-column type="index" width="30" />

      <el-table-column prop="rName" label="放映室" />

      <el-table-column prop="mName" label="电影名" />

      <el-table-column prop="start" label="开始时间">
        <template #default="scope">
          <el-tag
            :type="timeColor[index]"
            v-for="(i, index) in scope.row.start.split('T')"
            :key="index"
            :style="{ marginRight: '6px' }"
            >{{ i }}</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column prop="end" label="结束时间">
        <template #default="scope">
          <el-tag
            :type="timeColor[index]"
            v-for="(i, index) in scope.row.end.split('T')"
            :key="index"
            :style="{ marginRight: '6px' }"
            >{{ i }}</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column prop="rest" label="剩余座位" />

      <el-table-column prop="rTotal" label="座位总数" />

      <el-table-column fixed="right" label="数据操作">
        <template #default="scope">
          <el-button type="primary" @click="button('seat', scope.row)">
            查看座位
          </el-button>
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
      :width="modelTitle === '座位信息' ? '800' : '500'"
      align-center
    >
      <span v-if="modelTitle === '座位信息'">
        <div class="seatBox">
          <div
            :class="`seat status-${item.status}`"
            v-for="item in s"
            :key="item.sId"
          >
            {{ item.l }}列{{ item.r }}行
          </div>
        </div>
      </span>
      <span v-else>
        <el-form
          ref="formRef"
          v-if="!isInfo"
          :model="modelData"
          label-width="auto"
        >
          <el-form-item label="放映室名称">
            <el-input v-model="modelData.rName" />
          </el-form-item>

          <el-form-item label="电影名称">
            <el-input v-model="modelData.mName" />
          </el-form-item>

          <el-form-item label="开始时间">
            <el-date-picker
              v-model="modelData.start"
              type="datetime"
              format="YYYY年MM月DD日 HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
            />
          </el-form-item>

          <el-form-item label="结束时间">
            <el-date-picker
              v-model="modelData.end"
              type="datetime"
              format="YYYY年MM月DD日 HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
            />
          </el-form-item>
          <el-form-item label="剩余座位">
            <el-input v-model="modelData.rest" />
          </el-form-item>
          <el-form-item label="座位总数">
            <el-input v-model="modelData.rTotal" />
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
import { room, seat } from "@/api/request";
import { ElMessage } from "element-plus";

/**
 * Room 数据
 */
const list = reactive([
  {
    rId: "",
    rName: "",
    mId: "",
    start: "",
    end: "",
    rTotal: "",
    rest: "",
    mName: "",
    Intro: "",
  },
]);

/**
 * seat 数据
 */
const s = reactive([
  {
    sId: "",
    rId: "",
    status: "",
    r: "",
    l: "",
  },
]);

/**
 * 查询数据对象
 */
const query = ref({
  current: 1,
  size: 10,
  pages: 0,
  total: 0,
  mName: "",
});

/**
 * 时间颜色
 */
const timeColor = ["primary", "success"];

const model = ref(false);
const modelTitle = ref("模态框");
const method = ref("");
const isInfo = ref(false);

/**
 * Room 泛型表
 */
interface Room {
  rId: number;
  rName: string;
  mId: number;
  start: string;
  end: string;
  rTotal: number;
  rest: number;
  mName: string;
}

/**
 * 默认空数据
 */
let modelNull = {
  rId: 0,
  rName: "",
  mId: 0,
  start: "",
  end: "",
  rTotal: 0,
  rest: 0,
  mName: "",
};

let modelData: Room = modelNull;

/**
 *  请求 Room 列表
 */
const getList = async () => {
  const { result, message }: any = await room.list(query.value);
  list.splice(0);
  list.push(...result.records);
  query.value = Object.assign({}, query.value, result.pagination);
  ElMessage({
    message: message,
    type: "success",
  });
};

/**
 * 插入 Room 数据
 * @param item 传入参数
 */
const insert = async (item: object) => {
  const { result, message }: any = await room.insert(item);
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

/**
 * 更新 Room 数据
 * @param item 传入参数
 */
const update = async (item: object) => {
  const { result, message }: any = await room.update(item);
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

/**
 * 删除 Room 数据
 * @param item
 */
const del = async (item: object) => {
  const { result, message }: any = await room.delete(item);
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

/**
 * 获取 Room 关联的 Seat 数据
 * @param item
 */
const getSeat = async (item: object) => {
  const { result, message }: any = await seat.list(item);
  s.splice(0);
  s.push(...result.records);
  ElMessage({
    message: message,
    type: "success",
  });
};

/**
 * 模态框按钮事件
 * @param operation
 * @param row
 */
const button = (operation: string, row: Room = modelNull) => {
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
    seat: () => {
      getSeat(row);
      modelTitle.value = "座位信息";
    },
  };

  operations[operation]();
  method.value = operation;
  model.value = true;
};

/**
 * 模态框确认按钮事件
 */
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
    seat: () => {
      console.log(method.value);
    },
  };

  operations[method.value]();
  isInfo.value = false;
  model.value = false;
};

/**
 * 模态框取消按钮事件
 */
const cancel = () => {
  isInfo.value = false;
  model.value = false;
};

let i = 0;
/**
 * 模态框关闭事件
 */
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

<style lang="scss" scoped>
@import url("@/assets/style/room.css");
</style>
