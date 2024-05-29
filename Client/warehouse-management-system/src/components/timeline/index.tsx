import moment from 'moment';
import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames/bind';
import style from './TimeLine.module.scss';
import TimelineItem from './TimeLineItem';

const cx = classNames.bind(style);

function getFormattedData(items: any, format = 'HH:mm') {
    const activities: any = {};
    items.forEach(
        (
            {
                ts,
                text,
                description,
                type,
                agentType,
            }: { ts: any; text: any; description: any; type: any; agentType: any },
            index: any,
        ) => {
            const date = moment(ts);
            console.log('date', date.format(format));
            const dateStr = date.format('DD MMM YYYY');
            const list = activities[dateStr] || [];
            list.push({
                time: date.format(format),
                text,
                description,
                key: index,
                type,
                agentType,
            });
            activities[dateStr] = list;
        },
    );
    return activities;
}

function Timeline({ items, format }: { items: any; format: any }) {
    console.log(items);
    const activities = getFormattedData(items, format);
    const dates = Object.keys(activities);
    return (
        <div className={cx('time-line-ctnr')}>
            {dates.map((d) => (
                <ul className={cx('time-line')} key={d}>
                    <li className={cx('time-label')}>
                        <span>{d}</span>
                    </li>
                    {activities[d].map(
                        ({
                            time,
                            text,
                            description,
                            key,
                            type,
                            agentType,
                        }: {
                            time: any;
                            text: string;
                            description: any;
                            key: any;
                            type: any;
                            agentType: any;
                        }) => (
                            <TimelineItem
                                time={time}
                                title={text}
                                description={description}
                                key={key}
                                type={type}
                                agentType={agentType}
                            />
                        ),
                    )}
                </ul>
            ))}
        </div>
    );
}

Timeline.propTypes = {
    items: PropTypes.arrayOf(
        PropTypes.shape({
            ts: PropTypes.string.isRequired,
            text: PropTypes.string.isRequired,
        }),
    ).isRequired,
    format: PropTypes.string,
};

export default Timeline;
