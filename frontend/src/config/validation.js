const isEmpty = (value, value2) => {
  if (value == null || value == 0 || value == "" || value.length == 0) {
    return true;
  }
  return false;
};

const isInvalidPattern = (value, pattern) => {
  const regex = new RegExp(pattern);
  return !regex.test(value);
};

const isUnequal = (value, value2) => {
  if (value != value2) {
    return true;
  }
  return false;
};

const isNotGreaterThan = (value, value2) => {
  if (value > value2) {
    return false
  }
  return true
}

export { isEmpty, isInvalidPattern, isUnequal, isNotGreaterThan };
