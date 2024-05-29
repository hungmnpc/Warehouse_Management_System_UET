import React, { useEffect, useRef, useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Container,
    Paper,
    Box,
    TextField,
    Button,
    List,
    ListItem,
    ListItemText,
    useTheme,
    Divider,
} from '@mui/material';
import { HistoryData, HistoryEvent } from '../../utils/interfaces';
import { Client, over } from 'stompjs';
import SockJs from 'sockjs-client';
import { UUID } from 'crypto';
import { BaseKey } from '@refinedev/core';
import { getUserNameCurrently } from '../../authProvider';

interface ChatUIProp {
    agentId?: UUID | BaseKey | undefined | any;
    agentType?: string;
    title?: string | '';
    data?: HistoryData[];
    refetch?: () => any;
}

interface ChatMessage {
    date: any;
    messages: HistoryData[] | any[];
}

let stompClient: Client | null = null;
const ChatUI = ({ agentId, agentType, title, data, refetch = () => {} }: ChatUIProp) => {
    const theme = useTheme();

    console.log('theme', theme.palette.mode);

    const [messages, setMessages] = useState<HistoryData[]>([]);
    const [inputValue, setInputValue] = useState<string>('');

    const [connected, setConnected] = useState<boolean>(false);

    const [messagesRender, setMessagesRender] = useState<ChatMessage[]>([]);

    useEffect(() => {
        if (connected && data) {
            console.log('Hello Effect');
            setMessages(data);
        }
    }, [connected]);

    useEffect(() => {
        let Sock = new SockJs('http://localhost:7010/ws');
        stompClient = over(Sock);
        stompClient.connect({}, onConnected, onError);
    }, []);

    const onConnected = () => {
        setConnected(true);
        refetch();
        if (stompClient !== null) {
            stompClient.subscribe(`/user/${agentId}/private`, onPrivateMessageReceived);
        }
    };

    const onPrivateMessageReceived = (payload: any) => {
        console.log('Before message', messages);
        console.log('payload 1', payload);
        let payloadData: HistoryEvent = JSON.parse(payload.body);
        console.log('payload', payloadData);
        setMessages((prevMessages) => {
            const updatedMessages = [
                ...prevMessages,
                {
                    id: payloadData.agentId,
                    agent_id: payloadData.agentId,
                    agent_type: payloadData.agentType,
                    content: payloadData.content,
                    userId: payloadData.userId,
                    username: payloadData.username,
                    ts: payloadData.ts,
                },
            ];
            return updatedMessages;
        });
    };

    const convertData = (messages: HistoryData[]) => {
        let dataGroup: ChatMessage[] = [];
        messages.forEach((message, index) => {
            const date = convertMillisecondsToDateTime(message.ts, 7);
            const day = date.split(' ')[0];
            const indexz = dataGroup.findIndex((element) => element.date === day);
            console.log('convert index', indexz);
            if (indexz !== -1) {
                dataGroup[indexz].messages.push(message);
            } else {
                dataGroup.push({
                    date: day,
                    messages: [message],
                });
            }
        });

        return dataGroup;
    };

    const onError = (error: any) => {
        console.log('error', error);
        setConnected(false);
    };

    const sendNote = () => {
        if (stompClient && agentId && agentType) {
            console.log('DQH SENDDING>>>>>>>>');
            let Message: HistoryEvent = {
                agentId: agentId,
                agentType: agentType,
                content: inputValue,
                ts: Date.now(),
                type: 'MESSAGE',
                userId: '1c51b706-5253-4425-9b77-deeb5969a3e6',
                username: getUserNameCurrently(),
            };
            stompClient.send('/app/message', {}, JSON.stringify(Message));
            setInputValue('');
        }
    };

    const listRef = useRef<HTMLDivElement>(null);

    // Hàm cuộn xuống cuối danh sách
    const scrollToBottom = () => {
        if (listRef.current) {
            listRef.current.scrollTop = listRef.current.scrollHeight;
        }
    };

    useEffect(() => {
        scrollToBottom(); // Cuộn khi messages thay đổi
        if (messages) {
            setMessagesRender(convertData(messages));
        }
    }, [messages]);
    useEffect(() => {
        scrollToBottom(); // Cuộn khi messages thay đổi
    }, [messagesRender]);

    return (
        <Container>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6">{title}</Typography>
                </Toolbar>
            </AppBar>

            <Box sx={{ backgroundColor: 'white' }} my={1}>
                <Paper
                    ref={listRef}
                    style={{
                        padding: '0 10px',
                        height: '500px',
                        overflowY: 'scroll',
                        backgroundColor: `${theme.palette.mode == 'light' ? 'black' : 'white'}`,
                        color: `${theme.palette.mode == 'light' ? 'white' : 'black'}`,
                    }}
                >
                    {messagesRender.map((group, index) => (
                        <div key={index}>
                            <Typography variant="h6" sx={{ my: 1 }}>
                                {group.date}
                            </Typography>
                            <Divider />
                            <List>
                                {group.messages.map((message, index) => (
                                    <HistoryChatUI data={message} key={index} />
                                ))}
                            </List>
                        </div>
                    ))}
                </Paper>
            </Box>

            {connected ? (
                <Box display="flex">
                    <TextField
                        fullWidth
                        variant="outlined"
                        placeholder="Type a message..."
                        value={inputValue}
                        onChange={(e) => {
                            console.log('message zz', messages);
                            setInputValue(e.target.value);
                        }}
                        onKeyPress={(e) => {
                            if (e.key === 'Enter') {
                                sendNote();
                            }
                        }}
                    />
                    <Button variant="contained" color="primary" onClick={sendNote} style={{ marginLeft: '8px' }}>
                        Send
                    </Button>
                </Box>
            ) : (
                <p>Connecting....</p>
            )}
        </Container>
    );
};

interface HistoryChatUIProp {
    data?: HistoryData;
}

const convertMillisecondsToDateTime = (milliseconds: number, offsetHours = 0) => {
    // Create a new Date object from the provided milliseconds
    const date = new Date(milliseconds);

    // Apply the time zone offset in hours
    const localTime = new Date(date.getTime() + offsetHours * 3600000);

    // Extract year, month, day, hour, minute, and second
    const year = localTime.getUTCFullYear();
    const month = (localTime.getUTCMonth() + 1).toString().padStart(2, '0');
    const day = localTime.getUTCDate().toString().padStart(2, '0');
    const hours = localTime.getUTCHours().toString().padStart(2, '0');
    const minutes = localTime.getUTCMinutes().toString().padStart(2, '0');
    const seconds = localTime.getUTCSeconds().toString().padStart(2, '0');

    // Format the output as yyyy/mm/dd HH:MM:SS
    return `${year}/${month}/${day} ${hours}:${minutes}:${seconds}`;
};

const HistoryChatUI = ({ data }: HistoryChatUIProp) => {
    if (data) {
        const time = convertMillisecondsToDateTime(data.ts, 7);
        return (
            <p>
                {data.username} - {time.split(' ')[1]} : {data.content}
            </p>
        );
    }
    return <p>No data</p>;
};

export default ChatUI;
