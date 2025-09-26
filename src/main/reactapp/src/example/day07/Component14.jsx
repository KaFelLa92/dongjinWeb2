import Button from '@mui/joy/Button'
import Box from '@mui/joy/Box'
import Input from '@mui/joy/Input';
import Select from '@mui/joy/Select';
import Option from '@mui/joy/Option';
import Switch from '@mui/joy/Switch';
import Avatar from '@mui/joy/Avatar';
import Badge from '@mui/joy/Badge';
import Typography from '@mui/joy/Typography';
import List from '@mui/joy/List';
import ListItem from '@mui/joy/ListItem';
import ListItemButton, { listItemButtonClasses } from '@mui/joy/ListItemButton';
import IconButton from '@mui/joy/IconButton';
import { ReceiptLong, KeyboardArrowDown } from '@mui/icons-material';
import { useState } from 'react'

/* 
    MUI 조이UI 다운로드
    리액트 설치된 주소 : npm install @mui/joy @emotion/react @emotion/styled
*/

export default function Component14(props) {

    const [stock, setStock] = useState(0);

    const addStock = async () => {
        setStock(stock + 1);
    }

    const handleChange = (event, newValue) => {
        alert(`You chose "${newValue}"`);
    };

    const [checked, setChecked] = useState(false);

    const [open, setOpen] = useState(false);
    const [open2, setOpen2] = useState(false);

    return (<>
        <h3> MUI </h3>
        <p> 1) 소문자 마크업 : html , 대문자 마크업 : 컴포넌트 (임포트해야함) </p>
        <button> HTML </button>
        <Button variant="solid" onClick={addStock}> Hello world! ({stock}) </Button>
        <p> 2) 마크업 속성이란 : 마크업 안에 마크업 속성명=속성값 </p>

        <h3> 1. 버튼 : https://mui.com/joy-ui/react-button/ </h3>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
            <Button>Button</Button>
            <Button disabled>Disabled</Button>
            <Button loading>Loading</Button>
        </Box>

        <h3> 2. 입력상자 : https://mui.com/joy-ui/react-input/ </h3>
        <Input placeholder="Type in here…" />

        <h3> 3. 선택상자 : https://mui.com/joy-ui/react-select/ </h3>
        <Select defaultValue="dog" onChange={handleChange}>
            <Option value="dog">Dog</Option>
            <Option value="cat">Cat</Option>
            <Option value="fish">Fish</Option>
            <Option value="bird">Bird</Option>
        </Select>

        <h3> 4. 스위치 : https://mui.com/joy-ui/react-switch/ </h3>
        <Switch
            checked={checked}
            onChange={(event) => setChecked(event.target.checked)}
        />

        <h3> 5. 아바타 : https://mui.com/joy-ui/react-avatar/ </h3>
        <p> Box는 div와 같은 유형 , css 적용 방법 1. CSS파일 , 2. CSS객체</p>
        <Box sx={{ display: 'flex', gap: 2 }} style={{ backgroundColor: 'black' }}>
            <Avatar />
            <Avatar>JG</Avatar>
            <Avatar alt="Puppy" src="/1.jpg" />
        </Box>

        <h3> * 배지 : https://mui.com/joy-ui/react-badge/ </h3>
        <Badge badgeContent="cart" >
            <Typography sx={{ fontSize: 'xl' }}>🛒</Typography>
        </Badge>

        <h3> 6. 리스트 : https://mui.com/joy-ui/react-list/ </h3>
        <Box sx={{ width: 320, pl: '24px' }}>
            <List
                size="sm"
                sx={(theme) => ({
                    // Gatsby colors
                    '--joy-palette-primary-plainColor': '#8a4baf',
                    '--joy-palette-neutral-plainHoverBg': 'transparent',
                    '--joy-palette-neutral-plainActiveBg': 'transparent',
                    '--joy-palette-primary-plainHoverBg': 'transparent',
                    '--joy-palette-primary-plainActiveBg': 'transparent',
                    [theme.getColorSchemeSelector('dark')]: {
                        '--joy-palette-text-secondary': '#635e69',
                        '--joy-palette-primary-plainColor': '#d48cff',
                    },
                    '--List-insetStart': '32px',
                    '--ListItem-paddingY': '0px',
                    '--ListItem-paddingRight': '16px',
                    '--ListItem-paddingLeft': '21px',
                    '--ListItem-startActionWidth': '0px',
                    '--ListItem-startActionTranslateX': '-50%',
                    [`& .${listItemButtonClasses.root}`]: {
                        borderLeftColor: 'divider',
                    },
                    [`& .${listItemButtonClasses.root}.${listItemButtonClasses.selected}`]: {
                        borderLeftColor: 'currentColor',
                    },
                    '& [class*="startAction"]': {
                        color: 'var(--joy-palette-text-tertiary)',
                    },
                })}
            >
                <ListItem nested>
                    <ListItem component="div" startAction={<ReceiptLong />}>
                        <Typography level="body-xs" sx={{ textTransform: 'uppercase' }}>
                            Documentation
                        </Typography>
                    </ListItem>
                    <List sx={{ '--List-gap': '0px' }}>
                        <ListItem>
                            <ListItemButton selected>Overview</ListItemButton>
                        </ListItem>
                    </List>
                </ListItem>
                <ListItem sx={{ '--List-gap': '0px' }}>
                    <ListItemButton>Quick Start</ListItemButton>
                </ListItem>
                <ListItem
                    nested
                    sx={{ my: 1 }}
                    startAction={
                        <IconButton
                            variant="plain"
                            size="sm"
                            color="neutral"
                            onClick={() => setOpen(!open)}
                        >
                            <KeyboardArrowDown
                                sx={[
                                    open ? { transform: 'initial' } : { transform: 'rotate(-90deg)' },
                                ]}
                            />
                        </IconButton>
                    }
                >
                    <ListItem>
                        <Typography
                            level="inherit"
                            sx={[
                                open
                                    ? { fontWeight: 'bold', color: 'text.primary' }
                                    : { fontWeight: null, color: 'inherit' },
                            ]}
                        >
                            Tutorial
                        </Typography>
                        <Typography component="span" level="body-xs">
                            9
                        </Typography>
                    </ListItem>
                    {open && (
                        <List sx={{ '--ListItem-paddingY': '8px' }}>
                            <ListItem>
                                <ListItemButton>Overview</ListItemButton>
                            </ListItem>
                            <ListItem>
                                <ListItemButton>
                                    0. Set Up Your Development Environment
                                </ListItemButton>
                            </ListItem>
                            <ListItem>
                                <ListItemButton>
                                    1. Create and Deploy Your First Gatsby Site
                                </ListItemButton>
                            </ListItem>
                            <ListItem>
                                <ListItemButton>2. Use and Style React components</ListItemButton>
                            </ListItem>
                        </List>
                    )}
                </ListItem>
                <ListItem
                    nested
                    sx={{ my: 1 }}
                    startAction={
                        <IconButton
                            variant="plain"
                            size="sm"
                            color="neutral"
                            onClick={() => setOpen2((bool) => !bool)}
                        >
                            <KeyboardArrowDown
                                sx={[
                                    open2 ? { transform: 'initial' } : { transform: 'rotate(-90deg)' },
                                ]}
                            />
                        </IconButton>
                    }
                >
                    <ListItem>
                        <Typography
                            level="inherit"
                            sx={[
                                open2
                                    ? { fontWeight: 'bold', color: 'text.primary' }
                                    : { fontWeight: null, color: 'inherit' },
                            ]}
                        >
                            How-to Guides
                        </Typography>
                        <Typography component="span" level="body-xs">
                            39
                        </Typography>
                    </ListItem>
                    {open2 && (
                        <List sx={{ '--ListItem-paddingY': '8px' }}>
                            <ListItem>
                                <ListItemButton>Overview</ListItemButton>
                            </ListItem>
                            <ListItem>
                                <ListItemButton>Local Development</ListItemButton>
                            </ListItem>
                            <ListItem>
                                <ListItemButton>Routing</ListItemButton>
                            </ListItem>
                            <ListItem>
                                <ListItemButton>Styling</ListItemButton>
                            </ListItem>
                        </List>
                    )}
                </ListItem>
            </List>
        </Box>


    </>)

}

/*

    1. 리액트에서 CSS 적용하는 방법
        1안) CSS 파일을 생성한다 -> CSS 파일을 적용할 컴포넌트에서 import한다.
        2안) CSS객체 -> 컴포넌트에서 객체유형으로 CSS 작성한다. <컴포넌트명 style={{CSS카멜표기법}}
            주의할점 : -하이픈 대신에 카멜표가법 사용한다.
            { font-size : 10px } --> {font-size:"10"}

 */



