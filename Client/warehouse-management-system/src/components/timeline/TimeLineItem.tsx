import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames/bind';
import style from './TimeLine.module.scss';
import { Accordion, AccordionDetails, AccordionSummary, Typography } from '@mui/material';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { useOne } from '@refinedev/core';

const cx = classNames.bind(style);
interface TimelineItemProps {
    time: any;
    title: string;
    description: any;
    type: 'ADD' | 'CREATE' | 'DELETE' | 'UPDATE';
    agentType: 'purchase order' | 'warehouse';
}

function TimelineItem({ time, title, description, type, agentType }: TimelineItemProps) {
    console.log('description', description);

    return (
        <Accordion>
            <AccordionSummary expandIcon={<ArrowDownwardIcon />} aria-controls="panel1-content" id="panel1-header">
                {/* <Typography> */}
                <div style={{ display: 'flex', gap: '20px' }}>
                    <p>{time}</p>
                    <p>{title}</p>
                </div>
                {/* </Typography> */}
            </AccordionSummary>
            {description.map((data: any, index: any) => (
                <AccordionDetails key={index}>
                    {type === 'UPDATE' ? `${data.key} => ${data.value}` : `${data.key} ${data.value}`}
                </AccordionDetails>
            ))}
        </Accordion>
    );
}

export default TimelineItem;
