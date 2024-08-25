import { toYmdHm, ymdhm } from "@/lib/date";
import { Button, Flex, IconButton } from "@chakra-ui/react";
import ja from "date-fns/locale/ja";
import { forwardRef, useRef, useState } from "react";
import DatePicker, { registerLocale } from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {
  faCalendarCheck,
  faCalendarDay,
  faCalendarPlus,
  faCalendarWeek,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { type Locale, addDays } from "date-fns";

type CalendarSelectProps = {
  onDateChange: (date: Date | null) => void;
  placeholder?: string;
  selectedDate?: Date;
};

type CustomerHeaderProps = {
  onDateChange: (date: Date | null) => void;
};

registerLocale("ja", ja as unknown as Locale);

const CustomerHeader = ({ onDateChange }: CustomerHeaderProps) => {
  const handlePresetTodayChange = () => onDateChange(new Date());
  const handlePresetTomorrowChange = () => onDateChange(addDays(new Date(), 1));
  const handlePresetNextWeekChange = () => onDateChange(addDays(new Date(), 7));
  const handlePresetSomeDayChange = () => onDateChange(null);

  return (
    <Flex justifyContent="space-between" alignItems="center" p={2}>
      <IconButton
        icon={<FontAwesomeIcon icon={faCalendarDay} />}
        aria-label="Today"
        size="sm"
        onClick={handlePresetTodayChange}
      />
      <IconButton
        icon={<FontAwesomeIcon icon={faCalendarPlus} />}
        aria-label="Tomorrow"
        size="sm"
        onClick={handlePresetTomorrowChange}
      />
      <IconButton
        icon={<FontAwesomeIcon icon={faCalendarWeek} />}
        aria-label="Next week"
        size="sm"
        onClick={handlePresetNextWeekChange}
      />
      <IconButton
        icon={<FontAwesomeIcon icon={faCalendarCheck} />}
        aria-label="Someday"
        size="sm"
        onClick={handlePresetSomeDayChange}
      />
    </Flex>
  );
};

const CalendarSelect = (props: CalendarSelectProps) => {
  const [internalSelectedDate, setInternalSelectedDate] = useState<Date | null>(
    props.selectedDate ?? null,
  );

  const handleDateChange = (date: Date | null) => {
    setInternalSelectedDate(date);
    props.onDateChange(date);
  };

  const formattedDate = internalSelectedDate
    ? toYmdHm(internalSelectedDate)
    : props.placeholder ?? "未選択";

  const customInputButtonRef = useRef<HTMLButtonElement>(null);

  const CustomInput = forwardRef<
    HTMLButtonElement,
    React.ComponentProps<typeof Button>
  >((props, ref) => (
    <Button
      {...props}
      variant="link"
      color="inherit"
      _hover={{ textDecoration: "none" }}
      ref={ref}
      fontWeight="normal"
    >
      {formattedDate}
    </Button>
  ));

  return (
    <DatePicker
      selected={internalSelectedDate}
      onChange={handleDateChange}
      locale="ja"
      dateFormat={ymdhm}
      showTimeSelect
      timeFormat="HH:mm"
      timeIntervals={15}
      timeCaption="時間"
      customInput={<CustomInput ref={customInputButtonRef} />}
      popperPlacement="bottom-end"
      renderCustomHeader={() => (
        <CustomerHeader onDateChange={handleDateChange} />
      )}
    />
  );
};

export default CalendarSelect;
