import { format } from "date-fns";
import { ja } from "date-fns/locale";

export const ymdhms = "yyyy-MM-dd HH:mm:ss";
export const ymdhm = "yyyy-MM-dd HH:mm";

export const toYmdHm = (date: Date) => {
  return format(date, ymdhm, { locale: ja });
};

export const toYmdhms = (date: Date) => {
  return format(date, ymdhms, { locale: ja });
};

export const convertDate = (dateStr?: string) => {
  if (!dateStr) {
    return undefined;
  }

  return new Date(dateStr);
};
